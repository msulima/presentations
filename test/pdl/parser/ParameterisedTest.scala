package pdl.parser

import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers

trait ParameterisedTest[A, B] extends ShouldMatchers {
  this: FlatSpec =>

  def run(arg: A): B

  implicit class ForArgs(string: ParameterisedTest.this.type#ItVerbString) {
    def forArgs(args: (A, B)) {
      string in {
        run(args._1) should be(args._2)
      }
    }
  }

}
