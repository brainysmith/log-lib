package com.identityblitz.log

import java.util.logging.Level
import org.slf4j.bridge.SLF4JBridgeHandler
import scala.util.control.NonFatal
import org.slf4j.LoggerFactory
import ch.qos.logback.classic.LoggerContext
import ch.qos.logback.classic.joran.JoranConfigurator
import ch.qos.logback.core.util.StatusPrinter
import com.identityblitz.log.service.ServiceProvider
import java.net.URL
import java.io.File

/**
 *
 */
object LogConf {

  private val CONF_URL_CONF_NAME = "conf-url"
  private val DIR_CONF_NAME = "dir"
  private val LEVELS_CONF_NAME = "levels"

  private val confSp = ServiceProvider.confService

  def doConfigure(cl: ClassLoader, appName: String) {

    Option(java.util.logging.Logger.getLogger("")).map{root =>
      root.setLevel(Level.FINEST)
      root.getHandlers.foreach(root.removeHandler)
    }

    SLF4JBridgeHandler.install()

    try {
      val loggerCtx = LoggerFactory.getILoggerFactory.asInstanceOf[LoggerContext]
      loggerCtx.stop()
      val configurator = new JoranConfigurator
      configurator.setContext(loggerCtx)
      loggerCtx.reset()
      loggerCtx.putProperty("app.name", appName)
      loggerCtx.putProperty("dir.logs", confSp.getOptString(DIR_CONF_NAME).getOrElse{
        System.getProperty("user.home") + File.separator + "blitz"
      })

      try {
        confSp.getOptString(CONF_URL_CONF_NAME).map(new URL(_))
          .orElse(Option(cl.getResource("blitz-logger.xml")))
          .orElse{
          println("log-lib: use the default logger configuration")
          Option(LogConf.getClass.getClassLoader.getResource("default-logger.xml"))
        }.map(configurator.doConfigure)

        confSp.getMapString(LEVELS_CONF_NAME).foreach{
          case (logger, level) =>
            printf("log-lib: for '%s' logger set '%s' level\n", logger, level)
            loggerCtx.getLogger(logger).setLevel(ch.qos.logback.classic.Level.toLevel(level))
        }
        loggerCtx.start()
      }
      catch {
        case NonFatal(e) => e.printStackTrace()
      }
      StatusPrinter.printIfErrorsOccured(loggerCtx)
    }
    catch {
      case NonFatal(_) =>
    }

  }

}
