name := "urban-observatory"
organization := "com.github.jonnylaw"
version := "0.0.1-SNAPSHOT"

scalaVersion := "2.12.3"

scalacOptions ++= Seq(
  "-encoding", "UTF-8",   // source files are in UTF-8
  "-deprecation",         // warn about use of deprecated APIs
  "-unchecked",           // warn about unchecked type parameters
  "-feature",             // warn about misused language features
  "-language:higherKinds",// allow higher kinded types without `import scala.language.higherKinds`
  "-Xlint",               // enable handy linter warnings
  "-Xfatal-warnings",     // turn compiler warnings into errors
  "-Ypartial-unification" // allow the compiler to unify type constructors of different arities
)

libraryDependencies ++= Seq(
  "io.spray"               %% "spray-json"         % "1.3.3",
  "com.github.finagle"     %% "finch-circe"        % "0.16.0",
  "com.github.finagle"     %% "finch-core"         % "0.16.0",
  "com.github.nscala-time" %% "nscala-time"        % "2.18.0",
  "com.nrinaudo"           %% "kantan.csv-generic" % "0.3.1",
  "com.typesafe"           % "config"              % "1.3.1",
  "org.scalatest"          %% "scalatest"          % "3.0.4" % "test"

)

addCompilerPlugin("org.spire-math" %% "kind-projector" % "0.9.3")
