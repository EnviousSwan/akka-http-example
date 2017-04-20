sealed class RequestType
object GetRequest extends RequestType
object PostRequest extends RequestType

object Calculator {

	def calculate(args: Double*): Double = args match {
		case Seq(v1) =>
			if (v1 > 10) v1 - 10 else v1

		case Seq(v2, v3) =>
			if ((v3 + v2) < 10) v3 + v2 + 10
			else v3 + v2

		case _ => 0
	}

	def checkCondition(v2: Double, v3: Double): Boolean =
		v2 + v3 < 10
}
