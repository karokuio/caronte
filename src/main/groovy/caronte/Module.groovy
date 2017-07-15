package caronte

import com.google.inject.Scopes
import com.google.inject.Provides
import com.google.inject.AbstractModule

import pluto.Module as PlutoModule
import pluto.util.Yaml
import pluto.events.RabbitConsumer

/**
 * Guice module responsible to bootstrap all required
 * dependencies
 *
 * @since 0.1.0
 */
class Module extends AbstractModule {
  @Override
  void configure() {
    // Pluto's dependencies
    install(new PlutoModule())

    // Worker's own dependencies
    bind(RabbitConsumer).to(Consumer).in(Scopes.SINGLETON)
    bind(Service).in(Scopes.SINGLETON)
    bind(Application).in(Scopes.SINGLETON)
  }

  /**
   * Loads the specific configuration of this worker
   *
   * @return an instance of {@link Config}
   * @since 0.1.0
   */
  @Provides
  Config loadConfig() {
    return Yaml.loadAs("/caronte.yaml", Config)
  }
}
