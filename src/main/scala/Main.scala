import clean.lib.Ds
import ml.classifiers.MLP
import traits.Arg
import util.Datasets

object Main extends Arg with RHeatMap {
  lazy val (unlabeledStyleFore, unlabeledStyleBack) = "mark=text, mark options={solid, scale=1, ultra thick}" -> "gray, mark=*, mark options={solid, scale=2, ultra thick}"
  lazy val (labeledStyleFore, labeledStyleBack) = "mark=text, mark options={solid, scale=1.1, ultra thick}" -> "white, mark=*, mark options={solid, scale=2.3, ultra thick}"
  lazy val formats = Seq(
    ("color=teal, text mark=\\bf{a}, " + labeledStyleFore, labeledStyleBack),
    ("color=blue, text mark=\\bf{b}, " + labeledStyleFore, labeledStyleBack),
    ("color=yellow, text mark=\\bf{?}, " + unlabeledStyleFore, unlabeledStyleBack),
    ("color=red, text mark=\\bf{c}, " + labeledStyleFore, labeledStyleBack)
  )

  run()

  def run(): Unit = {
    val (patts, testSet) = Ds("fig.gif", readOnly = true).patterns -> Ds("fig2.gif", readOnly = true).patterns
    if (patts.head.nclasses != testSet.head.nclasses) sys.error("Training and testing sets have different number of classes.")
    val unlabeledClass = patts.groupBy(_.label).maxBy(_._2.size)._1
    val (labeled, unlabeled) = patts.partition(_.label != unlabeledClass)
    val pattsIg = Datasets.reweighted(unlabeled.map(_.relabeled(0)) ++ labeled.map(_.relabeled(1)))

    val first = (d: Array[Double]) => d(0)
    val max = (d: Array[Double]) => d.sorted.reverse(0)
    val margin = (d: Array[Double]) => 1 - (d.sorted.reverse(0) - d.sorted.reverse(1))

    val symbs = patts.groupBy(_.label).values.zip(formats).toList
    Seq(
      //      ("certainty", labeled, max)
      ("ignorance", pattsIg, first, "(255,100,200)")
      , ("decision-boundary", labeled, margin, "(100,200,255)")
      //            , ("knowledge-boundary", pattsIg, margin)
    ) foreach { case (name, set, f, colormap) => MLP().build(set).heatmap(name, testSet, f, symbs, colormap) }
  }
}
