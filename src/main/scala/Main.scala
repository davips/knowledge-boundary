import clean.lib.Ds
import ml.Pattern
import ml.classifiers.RF

object Main extends Arg {
  def run(): Unit = {
    val l = RF()
    val patts = reweighted(Ds("fig.gif", readOnly = true).patterns)
    patts foreach (p => println(p.toStrWeighted))
  }

  /**
    * Reweights instances to balance all classes equally.
    *
    * @param patts original instances
    * @return reweighted instances
    */
  def reweighted(patts: Seq[Pattern]): Seq[Pattern] = {
    val groupedPatts = patts groupBy (_.label)
    val (majority, minorities) = groupedPatts.toList.sortBy(_._2.size).map(_._2).reverse.splitAt(1)
    val majorityClassWeight = majority.flatten.size.toDouble
    majority.flatten ++ (minorities flatMap { p =>
      val w = majorityClassWeight / p.size
      p.map(_.reweighted(w))
    })
  }

  run()
}
