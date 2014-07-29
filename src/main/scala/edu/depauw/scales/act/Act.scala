package edu.depauw.scales.act

import scala.scalajs.js
import js.annotation.JSExport
import org.scalajs.dom
import rx._

import edu.depauw.scales.graphics._
import edu.depauw.scales.reactive._
import edu.depauw.scales.music._

import Reactive._

// Graphic, Note, and Anim all extends Scales
trait Scales {
	def act(time: Double = 0): Unit
	def duration: Double	
	def bounds: Bounds
	def translate(dx: Double, dy: Double): Scales
	def scale(x: Double): Scales //added
	def transformAct(scale: Double): Scales
}

trait Performance {
	def act(): Unit

	def on(that: Performance): ParallelAct = ParallelAct(this, that)

  def beside(that: Performance): Performance = {
    val dx = bounds.right - that.bounds.left
    on(that.translate(dx, 0))
  }

  def above(that: Performance): Performance = {
    val dy = bounds.bottom - that.bounds.top
    on(that.translate(0, dy))
  }
  
  def translate(dx: Double, dy: Double): Performance

  //added
	def displayOn(canvas: dom.HTMLCanvasElement = Canvas.canvas): Unit

	//added
	def scale(x: Double): Performance
  
	def seq(that: Performance): SequentialAct = SequentialAct(this, that)

	def transform(scale: Double): Performance

	def length: Double
	
	def bounds: Bounds

}

/*
** Note, Graphic, and Anim extend Scales
** Act extends Performance
** Performance handles Scales
*/
case class Act(scales: Scales) extends Performance {
	def length = scales.duration

	def act(): Unit = {
		scales.act(0)
	}
	
	def translate(dx: Double, dy: Double): Performance =
	  Act(scales.translate(dx, dy))

	def displayOn(canvas: dom.HTMLCanvasElement = Canvas.canvas): Unit = {
		val ctx = Canvas.ctx
    val scaleFactor = (canvas.width / bounds.width) min (canvas.height / bounds.height)
    this.scale(scaleFactor).act()
	}

	def scale(x: Double): Performance = {
		Act(scales.scale(x))
	}

	def transform(scale: Double): Performance = {
		Act(scales.transformAct(scale))
	}

	def bounds = scales.bounds
}

case class ParallelAct(one: Performance, two: Performance) extends Performance {
	def length = math.max(one.length, two.length)

	def act(): Unit = {
		one.act()
	  two.act()
	}

	def translate(dx: Double, dy: Double): Performance =
	  ParallelAct(one.translate(dx, dy), two.translate(dx, dy))

	def displayOn(canvas: dom.HTMLCanvasElement = Canvas.canvas): Unit = {
		val ctx = Canvas.ctx
		val scaleFactor = (canvas.width / bounds.width) min (canvas.height / bounds.height)
		ParallelAct(one.scale(scaleFactor), two.scale(scaleFactor)).act()
	}

	def scale(x: Double): Performance = {
		ParallelAct(one.scale(x), two.scale(x))
	}
	  
	def transform(scale: Double): Performance = {
		ParallelAct(one.transform(scale), two.transform(scale))
	}

	def bounds = one.bounds union two.bounds
}

case class SequentialAct(first: Performance, second: Performance) extends Performance {
	def length = first.length + second.length

	def act(): Unit = {
		first.act()

		val waitDuration = first.length
		dom.setTimeout(() => {
			second.act()
		}, waitDuration * 1000)

	}

	def translate(dx: Double, dy: Double): Performance =
	  SequentialAct(first.translate(dx, dy), second.translate(dx, dy))

	def displayOn(canvas: dom.HTMLCanvasElement = Canvas.canvas): Unit = {
		val ctx = Canvas.ctx
		val scaleFactor = (canvas.width / bounds.width) min (canvas.height / bounds.height)
		SequentialAct(first.scale(scaleFactor), second.scale(scaleFactor)).act()
	}

	def scale(x: Double): Performance = {
		SequentialAct(first.scale(x), second.scale(x))
	}
	  
	def transform(scale: Double): Performance = {
		SequentialAct(first.transform(scale), second.transform(scale))
	}

	def bounds = first.bounds union second.bounds
}
