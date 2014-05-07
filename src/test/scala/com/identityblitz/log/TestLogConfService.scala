package com.identityblitz.log

import com.identityblitz.log.service.spi.LogConfService
import com.identityblitz.conf.BlitzConf

/**
 */
class TestLogConfService extends BlitzConf("log") with LogConfService {
}
