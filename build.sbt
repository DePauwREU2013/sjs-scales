// Turn this project into a Scala.js project by importing these settings
scalaJSSettings

organization := "edu.depauw"

description := "Scala.js library for functional reactive graphics and music"

name := "sjs-scales"

version := "0.1"

scalaVersion := "2.11.1"

scalacOptions ++= Seq( "-deprecation", "-feature" )

libraryDependencies ++= Seq(
    "org.scala-lang.modules.scalajs" %%% "scalajs-dom" % "0.6",
    "com.scalarx" %%% "scalarx" % "0.2.5",
    "org.scala-lang.modules.scalajs" %% "scalajs-jasmine-test-framework" % scalaJSVersion % "test"
)

seq(bintrayPublishSettings:_*)

licenses += ("MIT", url("http://opensource.org/licenses/MIT"))

pomExtra := (
  <scm>
    <url>git@github.com:DePauwREU2013/sjs-scales.git</url>
    <connection>scm:git:git@github.com:DePauwREU2013/sjs-scales.git</connection>
  </scm>
  <developers>
    <developer>
      <id>bhoward</id>
      <name>Brian Howard</name>
      <url>https://github.com/bhoward</url>
    </developer>
    <developer>
      <id>bekroogle</id>
      <name>Benjamin Kruger</name>
      <url>https://github.com/bekroogle</url>
    </developer>
    <developer>
      <id>namchid</id>
      <name>Namchi Do</name>
      <url>https://github.com/namchid</url>
    </developer>
  </developers>
)
