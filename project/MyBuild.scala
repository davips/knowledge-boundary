import sbt._
import Keys._

object MyBuild extends Build {

  lazy val root: Project = Project("image2arff", file("."))
//    .aggregate(subimage, args)
    .dependsOn(args)
    .dependsOn(als)
    .dependsOn(mls)

  lazy val args = ProjectRef(uri("https://github.com/davips/args.git"), "args")
  lazy val als = ProjectRef(uri("https://github.com/active-learning/active-learning-scala.git"), "als")
  lazy val mls = ProjectRef(uri("https://github.com/machine-learning-scala/mls.git"), "mls")

}

