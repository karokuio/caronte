package caronte

/**
 * Defines all event keys
 *
 * @since 0.1.0
 */
class Events {

  /**
   * Event produced when a template entry has been registered in the
   * database
   *
   * @since 0.1.0
   */
  static final String TEMPLATE_CREATED = 'event.template.created'

  /**
   * Event produced when an existing template entry has been deleted
   * from the database
   *
   * @since 0.1.0
   */
  static final String TEMPLATE_DELETED = 'event.template.deleted'

  /**
   * Event produced when a template's Docker image has been created
   *
   * @since 0.1.0
   */
  static final String TEMPLATE_IMAGE_CREATED = 'event.template.image.created'

  /**
   * Event produced when a template's Docker image has been deleted
   *
   * @since 0.1.0
   */
  static final String TEMPLATE_IMAGE_DELETED = 'event.template.image.deleted'
}
