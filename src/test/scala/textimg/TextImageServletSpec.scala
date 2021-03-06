package textimg

import org.scalatra.test.specs2._

// For more on Specs2, see http://etorreborre.github.com/specs2/guide/org.specs2.guide.QuickStart.html
class TextImageServletSpec extends ScalatraSpec { def is =
  "GET /img?str=hoge on TextImageServlet"                     ^
    "should return status 200"                  ! root200^
                                                end

  addServlet(classOf[TextImageServlet], "/img?str=hoge")

  def root200 = get("/") {
    status must_== 200
  }
}
