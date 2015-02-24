name := """analytics-api"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  "mysql" % "mysql-connector-java" % "5.1.21",
  "org.scalaj" %% "scalaj-http" % "1.1.4"
)
