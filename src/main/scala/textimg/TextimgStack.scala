package textimg

import org.scalatra._
import scalate.ScalateSupport
import org.fusesource.scalate.TemplateEngine
import org.fusesource.scalate.layout.DefaultLayoutStrategy
import javax.servlet.http.{HttpServletResponse, HttpServletRequest}
import collection.mutable
import org.slf4j.{LoggerFactory, Logger}

trait TextimgStack extends ScalatraServlet with ScalateSupport {

  /* wire up the precompiled templates */
  override protected def defaultTemplatePath: List[String] = List("/WEB-INF/templates/views")

  override protected def createTemplateEngine(config: ConfigT) = {
    val engine = super.createTemplateEngine(config)
    engine.layoutStrategy = new DefaultLayoutStrategy(engine,
      TemplateEngine.templateTypes.map("/WEB-INF/templates/layouts/default." + _): _*)
    engine.packagePrefix = "templates"
    engine
  }

  /* end wiring up the precompiled templates */

  override protected def templateAttributes(implicit request: HttpServletRequest): mutable.Map[String, Any] = {
    super.templateAttributes ++ mutable.Map.empty // Add extra attributes here, they need bindings in the build file
  }


  notFound {
    // remove content type in case it was set through an action
    contentType = null
    // Try to render a ScalateTemplate if no route matched
    findTemplate(requestPath) map {
      path =>
        contentType = "text/html"
        layoutTemplate(path)
    } orElse serveStaticResource() getOrElse resourceNotFound()
  }

  val log = LoggerFactory.getLogger(getClass)

  errorHandler = {
    case rt: RuntimeException => {
      log.error("システムエラー発生 (RuntimeException) Path=:" + Option(request).fold("")(_.getRequestURI) +
        "\n  ErrorMessage=" + rt.getMessage, rt)
      status = HttpServletResponse.SC_BAD_REQUEST
      "Exception occured!"
    }

    case rt: Throwable => {
      log.error("システムエラー発生 (RuntimeException以外) :\n  ErrorMessage=" + rt.getMessage, rt)
      throw rt
    }
  }


  override def handle(req: HttpServletRequest, res: HttpServletResponse): Unit = {
    val path = req.getRequestURI
    val startMillis = System.currentTimeMillis()
    log.info( """[AccessLog] Path={}""", path)
    try {
      super.handle(req, res)
    } finally {
      val elapsedMillis = System.currentTimeMillis() - startMillis
      log.debug( """[EndLog]elapsedMillis={} ,Path={}""", elapsedMillis, path)
    }
  }

}
