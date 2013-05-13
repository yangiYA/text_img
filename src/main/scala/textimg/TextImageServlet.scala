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

  get("/") { html }
  get("/img", params.getOrElse("str", "").length != 0) {
    val imgType = "png"
    contentType = "image/" + imgType
    Text2Image.makeImageByteArray(params.get("str") + "", None, None, extension = imgType)
  }
  get("/img.png", params.getOrElse("str", "").length != 0) {
    val imgType = "png"
    contentType = "image/" + imgType
    Text2Image.makeImageByteArray(params.get("str") + "", None, None, extension = imgType)
  }
}
