package com.blitz.log

import org.scalatest._
import org.slf4j.LoggerFactory
import java.io.File
import com.blitz.log.service.ServiceProvider

/**
 */


class LogConfTest extends FlatSpec with Matchers {

  val root = LoggerFactory.getLogger("root")
  val blitzLogger = LoggerFactory.getLogger("com.blitz")
  val scsLogger = LoggerFactory.getLogger("com.blitz.scs")
  val cryptLogger = LoggerFactory.getLogger("com.blitz.crypt")
  val unspecifiedLogger = LoggerFactory.getLogger("com.unspecified")

  val appName = "test"
  val logFilePath = ServiceProvider.confService.dirOfLogs + s"/app-$appName.log"

  "LongConf" should "correct configure of the loggers and should exist specified in configuration file." in {
    LogConf.doConfigure(getClass.getClassLoader, appName)
    new File(logFilePath).exists() shouldBe true
  }

  "the root logger" should "have 'DEBUG' level instead of 'TRACE' defined in the logger's configuration file." in {
    root.isTraceEnabled shouldBe false
    root.isDebugEnabled shouldBe true
  }

  "the blitz logger" should "have 'INFO' (it specified in the logger's configuration file)." in {
    blitzLogger.isInfoEnabled shouldBe true
    blitzLogger.isDebugEnabled shouldBe false
  }

  "the scs logger" should "have 'TRACE' level instead of 'DEBUG' defined in the logger's configuration file." in {
    scsLogger.isTraceEnabled shouldBe true
  }

  "the crypt logger" should "have 'INFO' level as blitz logger." in {
    cryptLogger.isInfoEnabled shouldBe true
    cryptLogger.isDebugEnabled shouldBe false
  }

  "the unspecified logger" should "have 'DEBUG' level as root logger." in {
    unspecifiedLogger.isTraceEnabled shouldBe false
    unspecifiedLogger.isDebugEnabled shouldBe true
  }

  s"the $logFilePath file" should "have 2 lines" in {
    root.debug("root DEBUG test message")
    blitzLogger.debug("blitz DEBUG test message")
    blitzLogger.info("blitz INFO test message")
    io.Source.fromFile(logFilePath).getLines().size shouldBe 2
  }

}
