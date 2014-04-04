package com.identityblitz.log

import com.identityblitz.log.service.spi.LogConfService

/**
 */
class SimpleLogConfService extends LogConfService {

  override val levelsOfLogs: Map[String, String] = Map("root" -> "DEBUG", "com.identityblitz.scs" -> "TRACE")

  override val confUrlOfLogs: Option[String] = None

  /*override val confUrlOfLogs: Option[String] = Some("file:./src/test/logs/logger.xml")*/

  override val dirOfLogs: String = "./target/logs"
}
