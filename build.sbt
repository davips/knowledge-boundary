name := "knowledge-boundary"

version := "1.0"

scalaVersion := "2.12.1"

lazy val root = Project("knowledge-boundary", sbt.file(".")).dependsOn(args).dependsOn(als)

lazy val args = ProjectRef(uri("https://github.com/davips/args.git"), "args")

lazy val mls = ProjectRef(uri("https://github.com/machine-learning-scala/mls.git"), "mls")

lazy val als = ProjectRef(uri("https://github.com/active-learning/active-learning-scala.git"), "als")

