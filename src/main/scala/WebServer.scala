import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer
import spray.json.PrettyPrinter

import scala.concurrent.Future
import scala.io.StdIn

final case class Entity(tuple: (Int, Int, Int))
final case class Response(status: String, data: Double)

object PrettyJsonFormatSupport {

	import spray.json.DefaultJsonProtocol._

	implicit val printer = PrettyPrinter
	implicit val prettyEntityFormat = jsonFormat1(Entity)
	implicit val prettyResponseFormat = jsonFormat2(Response)
}

object WebServer extends App {

	import PrettyJsonFormatSupport._

	implicit val system = ActorSystem("my-system")
	implicit val materializer = ActorMaterializer()
	implicit val executionContext = system.dispatcher

	val (host, port) = ("localhost", 8080)

	val route: Route = pathPrefix("rest" / "calc") {
		get {
			pathSuffix(IntNumber) { v1 =>
				val result = fetchItem(GetRequest)(v1)

				onSuccess(result) {
					case Some(item) => complete(Response("success", item))
					case None => complete(Response("failure", 0))
				}
			}
		} ~
			post {
				pathEndOrSingleSlash {
					entity(as[Entity]) { item =>
						val (v2, v3, v4) = item.tuple
						val result = fetchItem(PostRequest)(v2, v3, v4)

						onSuccess(result) {
							case Some(res) => complete(Response("success", res))
							case None => complete(Response("failure", 0))
						}
					}
				}
			}
	}

	val bindingFuture = Http().bindAndHandle(route, host, port)

	println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
	StdIn.readLine()
	bindingFuture
		.flatMap(_.unbind())
		.onComplete(_ => system.terminate())

	private def fetchItem(requestType: RequestType)(input: Int*): Future[Option[Double]] =
		Future {
			(requestType, input) match {

				case (GetRequest, Seq(v1)) =>
					CSVStore.getValue(Intermediate)(v1) match {
						case a: Some[_] => a.map(Calculator.calculate(_))
						case None => None
					}

				case (PostRequest, Seq(v2, v3, v4)) =>
					val value3 = CSVStore.getValue(Input)(v3)
					val value4 = CSVStore.getValue(Intermediate)(v4)

					if (value3.isEmpty || value4.isEmpty) None
					else {
						val result = Calculator.calculate(v2, value3.get)
						CSVStore.updateIntermediate(v4, result)

						if (Calculator.checkCondition(v2, value3.get)) None
						else Some(1)
					}

				case _ => None
			}
		}
}