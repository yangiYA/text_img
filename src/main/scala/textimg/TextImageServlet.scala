package textimg

import org.scalatra._
import javax.imageio.ImageIO
import javax.servlet.ServletOutputStream

class TextImageServlet extends TextimgStack {

  val html: scala.xml.Elem = <html>
                               <body>
                                 <h1>Hello, world!</h1>
                                 Say<a href="hello-scalate">hello to Scalate { """hogehoge""" }</a>
                                 .
                               </body>
                             </html>
  val aaaa = """\d+"""
  /*
  get("/") {
    <html>
      <body>
        <h1>Hello, world!</h1>
        Say<a href="hello-scalate">hello to Scalate</a>
        .
      </body>
    </html>
  }
 */

  get("/*") { NotFound("""It's could not be found""") }
  get("/img", params.getOrElse("str", "").length != 0) {
    val imgType = "png"
    contentType = "image/" + imgType
    Text2Image.makeImageByteArray(params.get("str"), None, None, extension = imgType)
  }

  get("/img.:extension", params.getOrElse("str", "").length != 0) {
    val imgType = params("extension")
    val imgExtList: List[String] = List("png", "jpg", "jpeg", "gif")
    if (imgExtList.exists { _.equalsIgnoreCase(imgType) }) {
      contentType = "image/" + imgType
      Text2Image.makeImageByteArray(params.get("str"), None, None, extension = imgType)
    } else {
      NotFound("""It's could not be found!""")
    }
  }

  get("/img/:extension", params.getOrElse("str", "").length != 0) {
    val imgType = params("extension")
    val imgExtList: List[String] = List("png", "jpg", "jpeg", "gif")
    if (imgExtList.exists { _.equalsIgnoreCase(imgType) }) {
      contentType = "image/" + imgType
      Text2Image.makeImageByteArray(params.get("str"), None, None, extension = imgType)
    } else {
      NotFound("""It's could not be found!""")
    }
  }

  //  get("/img.png", params.getOrElse("str", "").length != 0) {
  //    val imgType = "png"
  //    contentType = "image/" + imgType
  //    Text2Image.makeImageByteArray(params.get("str") + "", None, None, extension = imgType)
  //  }
}
