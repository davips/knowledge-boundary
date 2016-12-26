import sbt._
import Keys._

object MyBuild extends Build {

  lazy val root: Project = Project("knowledge-boundary", file("."))
    .dependsOn(args)
    .dependsOn(als)

  lazy val args = ProjectRef(uri("https://github.com/davips/args.git"), "args")
  lazy val als = ProjectRef(uri("https://github.com/active-learning/active-learning-scala.git"), "als")

}

