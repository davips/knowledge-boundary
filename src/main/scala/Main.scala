import clean.lib.Ds
import ml.classifiers.MLP
import traits.Arg
import util.Datasets

object Main extends Arg with RHeatMap{
  def run(): Unit = {
    val (patts, testSet) = Ds("fig.gif", readOnly = true).patterns -> Ds("fig2.gif", readOnly = true).patterns
    if (patts.head.nclasses != testSet.head.nclasses) sys.error("Training and testing sets have different number of classes.")
    val unlabeledClass = patts.groupBy(_.label).maxBy(_._2.size)._1
    val (labeled, unlabeled) = patts.partition(_.label != unlabeledClass)
    val pattsIg = Datasets.reweighted(unlabeled.map(_.relabeled(0)) ++ labeled.map(_.relabeled(1)))
    MLP().build(labeled).heatmap(testSet, (d: Array[Double]) => d.sorted.head)
  }

  run()
}
