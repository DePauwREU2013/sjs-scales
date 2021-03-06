package edu.depauw.scales.reactive

import scala.scalajs.js
import js.annotation.JSExport
import org.scalajs.dom
import rx._

import edu.depauw.scales.graphics._
import Base._

object Canvas {
  val canvas = dom.document.getElementById("output").asInstanceOf[dom.HTMLCanvasElement]
  val ctx = canvas.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]
}

object CanvasHandler {
  val ctx = Canvas.ctx
  val width = Canvas.canvas.width
  val height = Canvas.canvas.height

  val graphics: Var[List[Graphic]] = Var(Nil)

  def getIndex(): Int = {
    return graphics().size
  }

  def addGraphic(g: Graphic): Unit = { 
    graphics() = graphics() :+ g 
  }

  def updateGraphic(index: Int, g: Graphic): Unit = {
    graphics() = graphics().patch(index, List(g), 1)
  }

  Obs(graphics) {
    ctx.clearRect(0, 0, width, height)
    for(i <- 0 until graphics().length) {
      graphics()(i).render(ctx)
    }
  }
}