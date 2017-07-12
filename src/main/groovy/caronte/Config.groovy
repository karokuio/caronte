package caronte

/**
 * Worker's specific configuration
 *
 * @since 0.1.0
 */
class Config {

  /**
   * Underlying filesystem paths
   *
   * @since 0.1.0
   */
  Storage storage

  /**
   * Details about where to store or retrieve files
   * within the underlying filesystem
   *
   * @since 0.1.0
   */
  static class Storage {

    /**
     * Path to find the stored templates
     *
     * @since 0.1.0
     */
    String templates
  }
}
