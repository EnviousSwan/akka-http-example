name := "NewGroundTest"

version := "1.0"

scalaVersion := "2.12.2"

resolvers += Resolver.bintrayRepo("hseeberger", "maven")
resolvers += "Artima Maven Repository" at "http://repo.artima.com/releases"

libraryDependencies ++= List(
	"com.typesafe.akka" %% "akka-http" % "10.0.5",
	"com.typesafe.akka" %% "akka-http-spray-json" % "10.0.4",
	"org.scalactic" %% "scalactic" % "3.0.1",
	"org.scalatest" %% "scalatest" % "3.0.1" % "test",
	"io.spray" %%  "spray-json" % "1.3.3",
	"com.typesafe.akka" %% "akka-http-testkit" % "10.0.0"
)

