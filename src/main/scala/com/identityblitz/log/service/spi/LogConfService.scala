package com.identityblitz.log.service.spi

/**
 */
trait LogConfService {

  /**
   * Returns a configuration value corresponding to the specified name.
   * @param name - name of configuration parameter.
   * @return - [[Some]] with a parameter value if it specified otherwise [[None]].
   */
  def getOptString(implicit name: String): Option[String]

  /**
   * Returns a [[Map]] with properties filtered by specified prefix where:
   * <ul>
   *   <li>key - is a substring of the property's name from the specified prefix to the next position of the dot (''.'')</li>
   *   <li>value - value of the property</li>
   * </ul>
   *
   * @param prefix - prefix of the properties to include to the result.
   * @return [[Map]] with parameter name (exclude the specified prefix) and value. If there isn't configuration with
   *        the specified name returns empty [[Map]].
   */
  def getMapString(prefix: String): Map[String, String]
}
