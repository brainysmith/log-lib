package com.blitz.log

import java.util.logging.Level
import org.slf4j.bridge.SLF4JBridgeHandler
import scala.util.control.NonFatal
import org.slf4j.LoggerFactory
import ch.qos.logback.classic.LoggerContext
import ch.qos.logback.classic.joran.JoranConfigurator
import ch.qos.logback.core.util.StatusPrinter
import com.blitz.log.service.ServiceProvider
import java.net.URL

/**
 *
 */
object LogConf {

  def doConfigure(cl: ClassLoader, appName: String) {

    val conf = ServiceProvider.confService

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
      loggerCtx.putProperty("dir.logs", conf.dirOfLogs)

      try {
        conf.confUrlOfLogs.map(new URL(_)).orElse(Option(cl.getResource("logger.xml"))).orElse{
          println("error: URL of a logger configuration not specified and logger.xml not found in class path")
          throw new IllegalStateException("error: URL of a logger configuration not specified and logger.xml not " +
            "found in class path")
        }.map(configurator.doConfigure)

        conf.levelsOfLogs.foreach{
          case (logger, level) => loggerCtx.getLogger(logger).setLevel(ch.qos.logback.classic.Level.toLevel(level))
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
