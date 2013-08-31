import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName = "presentations"
  val appVersion = "0.0.2-SNAPSHOT"

  val appDependencies = Seq()

  lazy val akkaVersion = "2.1.0"
  lazy val scalatestVersion = "1.9.1"

  def customLessEntryPoints(base: File): PathFinder =
    base / "app" / "assets" / "stylesheets" * "*.less"

  val main = play.Project(appName, appVersion, appDependencies).settings(
    lessEntryPoints <<= baseDirectory(customLessEntryPoints),
    libraryDependencies ++= List(
      "com.typesafe.akka" %% "akka-actor" % akkaVersion withSources(),
      "com.typesafe.akka" %% "akka-testkit" % akkaVersion % "test" withSources(),
      "org.scalatest" %% "scalatest" % scalatestVersion % "test " withSources()
    )
  )
}
