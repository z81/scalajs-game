enablePlugins(ScalaJSPlugin)

name := "untitled"

version := "0.1"

scalaVersion := "2.12.4"

libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "0.9.1"
libraryDependencies += "be.doeraene" %%% "scalajs-jquery" % "0.9.1"

scalaJSUseMainModuleInitializer := true