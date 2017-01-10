import java.io.FileWriter

import ml.Pattern
import ml.classifiers.MLP
import traits.Util

import scala.sys.process._
import scala.language.postfixOps

trait RHeatMap extends Util {
  def script(w: Int, h: Int, midpoint: Double): String = {
    val s =
      """default <- function() {
        |  plot = theme_bw() + theme(legend.position="right",
        |  axis.title=element_text(size=14),
        |  strip.text=element_text(size=14),
        |  legend.title=element_text(size=14),
        |  legend.text=element_text(size=14),
        |  axis.text.x=element_text(size=14),
        |  axis.text.y=element_text(size=14));
        |  return(plot);
        |}

        |multiplot <- function(..., plotlist=NULL, file, cols=1, layout=NULL) {
        |  library(grid)
        |  # Make a list from the ... arguments and plotlist
        |  plots <- c(list(...), plotlist)
        |  numPlots = length(plots)
        |  # If layout is NULL, then use 'cols' to determine layout
        |  if (is.null(layout)) {
        |    # Make the panel
        |    # ncol: Number of columns of plots
        |    # nrow: Number of rows needed, calculated from # of cols
        |    layout <- matrix(seq(1, cols * ceiling(numPlots/cols)),
        |                    ncol = cols, nrow = ceiling(numPlots/cols))
        |  }
        | if (numPlots==1) {
        |    print(plots[[1]])
        |  } else {
        |    # Set up the page
        |    grid.newpage()
        |    pushViewport(viewport(layout = grid.layout(nrow(layout), ncol(layout))))
        |    # Make each plot, in the correct location
        |    for (i in 1:numPlots) {
        |      # Get the i,j matrix positions of the regions that contain this subplot
        |      matchidx <- as.data.frame(which(layout == i, arr.ind = TRUE))
        |      print(plots[[i]], vp = viewport(layout.pos.row = matchidx$row,
        |                                      layout.pos.col = matchidx$col))
        |    }
        |  }
        |}
        |
        |
        |library(ggplot2)
        |gg <- function (t,f) {
        |  ggplot(data=read.csv(f, sep=" ")) + geom_tile(aes(x, y, fill=z)) +
        |  xlim(0, $width$) + ylim(0, $height$) + ggtitle(t) +
        |  ggsave(paste(t,".pdf",sep=""))
        |}
        |u <- gg("pool max posteriori", "/run/shm/knowledge-boundary.csv")
        |multiplot(u, cols=1)
      """.stripMargin.replace("$width$", w.toString).replace("$height$", h.toString).replace("$midpoint$", midpoint.toString)
    println(s)
    s
  }
}
/*
default <- function() {
  plot = theme_bw() + theme(legend.position="right",
  axis.title=element_text(size=14),
  strip.text=element_text(size=14),
  legend.title=element_text(size=14),
  legend.text=element_text(size=14),
  axis.text.x=element_text(size=14),
  axis.text.y=element_text(size=14));
  return(plot);
}

multiplot <- function(..., plotlist=NULL, file, cols=1, layout=NULL) {
  library(grid)
  # Make a list from the ... arguments and plotlist
  plots <- c(list(...), plotlist)
  numPlots = length(plots)
  # If layout is NULL, then use 'cols' to determine layout
  if (is.null(layout)) {
    # Make the panel
    # ncol: Number of columns of plots
    # nrow: Number of rows needed, calculated from # of cols
    layout <- matrix(seq(1, cols * ceiling(numPlots/cols)),
                    ncol = cols, nrow = ceiling(numPlots/cols))
  }
 if (numPlots==1) {
    print(plots[[1]])
  } else {
    # Set up the page
    grid.newpage()
    pushViewport(viewport(layout = grid.layout(nrow(layout), ncol(layout))))
    # Make each plot, in the correct location
    for (i in 1:numPlots) {
      # Get the i,j matrix positions of the regions that contain this subplot
      matchidx <- as.data.frame(which(layout == i, arr.ind = TRUE))
      print(plots[[i]], vp = viewport(layout.pos.row = matchidx$row,
                                      layout.pos.col = matchidx$col))
    }
  }
}


library(ggplot2)
gg <- function (t,f) {
  ggplot(data=read.csv(f, sep=" ")) + geom_tile(aes(x, y, fill=z)) +
  xlim(0, 959) + ylim(0, 955) + ggtitle(t) +
  ggsave(paste(t,".pdf",sep=""))
}
u <- gg("pool max posteriori", "/run/shm/knowledge-boundary.csv")
multiplot(u, cols=1)

 */
