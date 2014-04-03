LOG-LIB
----------------------
This library aims to override the default place of the logging configuration file.
It allow set the URL to logging configuration file by library's spi.
If no URL specified the file the library try to find 'logger.xml' in the root class path of the specified class loader.
Also the library route all JUL log record to the SL4J API and make avaliable the following substitution string in the
the logging configuration file:
* dir.logs - a desired directory for the logs which must be specified by library's spi;
* app.name - a name of the application which use the library;

Example of the logging configuration file
----------------------------
```xml
<configuration>
  <appender name="FILE" class="ch.qos.logback.core.FileAppender">
     <file>${dir.logs}/app-${app.name}.log</file>
     <encoder>
       <pattern>%date - [%level] - from %logger in %thread: %message%n%xException</pattern>
     </encoder>
   </appender>
  <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
    <encoder>
      <pattern>%logger{15} - %message%n%xException{5}</pattern>
    </encoder>
  </appender>
  <logger name="com.blitz" level="INFO" />
  <logger name="com.blitz.scs" level="DEBUG" />
  <root level="TRACE">
    <appender-ref ref="STDOUT" />
    <appender-ref ref="FILE" />
  </root>
</configuration>
```

Requires
---------------
* [sbt 0.13.1](http://www.scala-sbt.org/)
* [logback classic 1.0.7] (http://logback.qos.ch/)
* [slf4j bridging legacy APIs] (http://www.slf4j.org/legacy.html)

Use
---------------
During the initializing app perform the following code:
``` java
    LogConf.doConfigure(getClass().getClassLoader(), "test-app")
```
Also need provide LogConfService. For example:
``` scala
class SimpleLogConfService extends LogConfService {
  override val levelsOfLogs: Map[String, String] = Map("root" -> "DEBUG", "com.blitz.scs" -> "TRACE")
  override val confUrlOfLogs: Option[String] = None
  /*override val confUrlOfLogs: Option[String] = Some("file:./src/test/logs/logger.xml")*/
  override val dirOfLogs: String = "./target/logs"
}
```
