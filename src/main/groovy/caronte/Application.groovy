package caronte

import pluto.PlutoApplication
import com.google.inject.Guice

/**
 * Worker's application
 *
 * @since 0.1.0
 */
class Application extends PlutoApplication {

  /**
   * Worker's main entry point
   *
   * @since 0.1.0
   */
  static void main(args) {
    Guice.createInjector(new Module()).getInstance(PlutoApplication).run()
  }
}
