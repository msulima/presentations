package domain.model

import domain.model.Slide.{Content, Theme}

case class Presentation(title: String, author: String, slides: Seq[Slide])

object Slide {

  type SlideId = String

  type Theme = String

  type Content = String
}

case class Slide(slideId: String, theme: Theme, content: Content)
