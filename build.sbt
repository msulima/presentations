name := """presentations"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.0"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache,
  ws
)

libraryDependencies += "org.scalatest" %% "scalatest" % "2.1.6" % "test " withSources()

libraryDependencies += "org.pegdown" % "pegdown" % "1.4.2"
