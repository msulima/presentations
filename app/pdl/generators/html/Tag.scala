package pdl.generators.html

import play.twirl.api.Html
import Tag._

case class Tag(tag: String) {

  def open = Html(s"<$tag>")
  def close = Html(s"</$tag>")

  def apply(content: Html): Html =
    Html(open.toString() + content + close)

  def apply(content: Seq[Html]): Html =
    apply(concat(content))
}

object Tag {
  val P = Tag("p")
  val LI = Tag("li")
  val UL = Tag("ul")

  def concat(content: Seq[Html]) =
    content.foldLeft(Html(""))((body, html) => Html(body.toString() + html))
}
