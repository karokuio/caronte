package caronte

import javax.inject.Inject
import groovy.json.JsonSlurper
import groovy.util.logging.Slf4j
import com.rabbitmq.client.AMQP
import com.rabbitmq.client.Envelope
import com.rabbitmq.client.ConnectionFactory
import pluto.events.RabbitConsumer

/**
 * Consumes messages to create or delete a given
 * template's image
 *
 * @since 0.1.0
 */
@Slf4j
class Consumer extends RabbitConsumer {

  /**
   * Worker's service logic
   *
   * @since 0.1.0
   */
  @Inject
  Service service

  /**
   * Default constructor
   *
   * @since 0.1.0
   */
  @Inject
  Consumer(ConnectionFactory factory) {
    super(factory)
  }

  @Override
  void handleDelivery(String consumerTag,
                      Envelope envelope,
                      AMQP.BasicProperties properties,
                      byte[] body) {

    Map message = new JsonSlurper().parse(body, 'UTF-8') as Map

    switch ("${envelope.routingKey}") {
      case Events.TEMPLATE_CREATED:
        log.info "creating template's image"
        service.buildTemplate(message)
        break
      case Events.TEMPLATE_DELETED:
        log.info "deleting template's image"
        service.deleteTemplate(message)
        break
      default:
        log.error "message of type: ${message.type} can't be processed!"
    }
  }
}
