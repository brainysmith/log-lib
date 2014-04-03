package com.blitz.log.service.spi

/**
 */
trait LogConfService {

  val dirOfLogs: String

  val confUrlOfLogs: Option[String]

  val levelsOfLogs: Map[String, String]
}
