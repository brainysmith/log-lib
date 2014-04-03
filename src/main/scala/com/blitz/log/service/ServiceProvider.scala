package com.blitz.log.service

import java.util.ServiceLoader
import com.blitz.log.service.spi.LogConfService

/**
 */
object ServiceProvider {

  lazy val confService = {
    val csItr = ServiceLoader.load(classOf[LogConfService]).iterator()
    if(!csItr.hasNext)
      throw new RuntimeException("log configuration service is undefined.")
    csItr.next()
  }

}
