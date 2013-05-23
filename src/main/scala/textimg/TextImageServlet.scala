package textimg

import org.scalatra._
import javax.imageio.ImageIO
import javax.servlet.ServletOutputStream

class TextImageServlet extends TextimgStack {

  val notFoundHtml =
    <html>
      <body>Page not found !</body>
    </html>

  get("/*")(NotFound(notFoundHtml))

  get("/img", params.getOrElse("str", "").length != 0) {
    imageResult(params.get("str"), "png")
  }

  get("/img.:extension", params.getOrElse("str", "").length != 0) {
    imageResult(params.get("str"), params("extension"))
  }

  get("/img/:extension", params.getOrElse("str", "").length != 0) {
    imageResult(params.get("str"), params("extension"))
  }

  get("/textimg/:string/img.:extension") {
    imageResult(Some(params("string")), params("extension"))
  }

  get("/exception") {
    throw new RuntimeException("path=/exception が要求されたので、例外を発生させました")
  }

  private def imageByteArrayActionResult(string: Option[String], imageType: String) = {
    contentType = "image/" + imageType.toLowerCase()
    Text2Image.makeImageByteArray(string, None, None, extension = imageType)
  }

  private def imageResult(string: Option[String], imageType: String) = {
    val imgExtList: List[String] = List("png", "jpg", "jpeg", "gif")
    if (imgExtList exists (_ equalsIgnoreCase imageType)) {
      imageByteArrayActionResult(string, imageType)
    } else {
      NotFound( """It's could not be found!""")
    }
  }

}
