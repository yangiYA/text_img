package textimg

import java.awt.Color
import java.awt.Graphics2D
import java.awt.image.BufferedImage
import scala.annotation.tailrec
import scala.runtime.SeqCharSequence
import java.io.InputStream
import javax.imageio.ImageIO
import java.io.ByteArrayOutputStream
import java.io.ByteArrayInputStream

/** 文字列をイメージに変換するユーティリティーメソッドを提供するオブジェクトです */
object Text2Image {
  def makeImage(
    str: String, color: Option[Color], backgroundColor: Option[Color], width: Int = 150, height: Int = 200): BufferedImage = {
    val bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR)
    val graph: Graphics2D = bi.createGraphics()
    val bgColor = backgroundColor.getOrElse(Color.LIGHT_GRAY);
    val fColor = backgroundColor.getOrElse(Color.BLACK);
    graph.setColor(fColor)
    graph.setBackground(bgColor)
    graph.clearRect(1, 1, width - 1, height - 1);
    val strings = split(str, width / 12)
    var cnt = 1
    strings.foreach { string => graph.drawString(string, 0, 14 * cnt); cnt = cnt + 1 }
    bi // 戻り値
  }

  def makeImageByteArray(
    str: Option[String], color: Option[Color], backgroundColor: Option[Color], width: Int = 150, height: Int = 200, extension: String = "png"): Array[Byte] = {
    val outputStream = new ByteArrayOutputStream
    val bufferedImage = makeImage(str.getOrElse(""), color, backgroundColor, width, height)
    ImageIO.write(bufferedImage, extension, outputStream);
    outputStream.toByteArray()
  }

  /** str を length 毎に分けてListで返却します */
  private def split(str: String, length: Int): List[String] = {

    /** accumulator に逆順にStringを積んて行くので注意*/
    @tailrec def split_reflexive_reverced(
      str: String, length: Int, accumulator: List[String] = Nil): List[String] = {

      str match {
        case null => accumulator
        case "" => accumulator
        case a if a.length() <= length => a :: accumulator
        case other => {
          val (fst, snd) = devide(str, length)
          split_reflexive_reverced(snd, length, fst :: accumulator)
        }
      }
    }
    def devide(str: String, length: Int): (String, String) = {
      str match {
        case null => ("", "")
        case shortStr if shortStr.length <= length => (str, "")
        case longStr => (str.substring(0, length), str.substring(length))
      }
    }

    split_reflexive_reverced(str, length).reverse
  }
}
