package domain.rendering

import domain.presentations.Presentation
import domain.presentations.Slide.SlideId

trait SlideRenderer extends HtmlGenerator {

  def render(presentation: Presentation, slideId: SlideId): Option[RenderedSlide] = {
    for ((slide, i) <- presentation.slides.zipWithIndex) {
      if (slide.slideId == slideId) {
        val content = toHtml(presentation.title, presentation.author, slide)
        return Some(RenderedSlide(slideId, content, getSlideId(presentation, i - 1), getSlideId(presentation, i + 1)))
      }
    }
    None
  }

  def getSlideId(presentation: Presentation, i: Int) =
    presentation.slides.lift(i).map(_.slideId)
}
