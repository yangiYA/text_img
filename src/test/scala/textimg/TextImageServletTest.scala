package textimg

import org.scalatest.FunSuite
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatra.test.scalatest.ScalatraSuite
import javax.servlet.http.HttpServletResponse._


/**
 * Created with IntelliJ IDEA.
 * User: yanagawa.h
 * Date: 13/05/21
 * Time: 12:37
 * To change this template use File | Settings | File Templates.
 */
@RunWith(classOf[JUnitRunner])
class TextImageServletTest extends ScalatraSuite with FunSuite {

  addServlet(classOf[TextImageServlet], "/*")

  val defaultCharsetVal = """;charset=UTF-8"""
  val removeCharset: (String) => Some[String] =
    x => Some(x.replaceAll( """[;]charset=.+$""", ""))

  test("get /img ") {
    get("/img?str=hoge") {
      status should equal(SC_OK) //200
      assert(header.get("Content-Type").flatMap(removeCharset) === Option("image/png"))
    }
  }

  test("get /img norParameter<str>") {
    get("/img") {
      status should equal(SC_NOT_FOUND) //404
    }
  }

  test("get /img/<extention>") {
    get("/img/gif?str=hoge") {
      status should equal(SC_OK) //200
      assert(header.get("Content-Type").flatMap(removeCharset) === Option("image/gif"))
    }
  }

  test("get /img/<NG extention>") {
    get("/img/ngextention?str=hoge") {
      status should equal(SC_NOT_FOUND) //404
    }
  }

  test("get /img.<extention>") {
    get("/img.PNG?str=foo") {
      println(header.get("Content-Type")) // debug sysout
      status should equal(SC_OK) //200
      assert(header.get("Content-Type").flatMap(removeCharset) === Option("image/png"))
    }
  }

  test("get /textimg/img.<extention>") {
    get("/img.Jpeg?str=hogehoge") {
      status should equal(SC_OK) //200
      assert(header.get("Content-Type").flatMap(removeCharset) === Option("image/jpeg"))
    }
  }


  test("get NG path /hoge")(get("/hoge")(status should equal(SC_NOT_FOUND))) //404
  test("get NG path /img/")(get("/img/")(status should equal(SC_NOT_FOUND))) //404
  test("get NG path /")(get("/")(status should equal(SC_NOT_FOUND))) //404

}
