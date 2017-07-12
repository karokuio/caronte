package caronte

import groovy.util.logging.Slf4j
import groovy.json.JsonOutput
import javax.inject.Inject
import java.nio.file.Files
import java.nio.file.Paths
import com.github.dockerjava.api.DockerClient
import com.github.dockerjava.core.command.BuildImageResultCallback
import com.github.dockerjava.api.model.BuildResponseItem
import org.zeroturnaround.zip.ZipUtil

import pluto.events.Publisher
import pluto.util.Storage

/**
 * Service responsible to hold the logic on how to create and remove
 * templates images from Docker
 *
 * @since 0.1.0
 */
@Slf4j
class Service {

  /**
   * Instance to deal with Docker instance
   *
   * @since 0.1.0
   */
  @Inject
  DockerClient dockerClient

  /**
   * Worker specific configuration
   *
   * @since 0.1.0
   */
  @Inject
  Config config

  /**
   * Pluto's configured publisher
   *
   * @since 0.1.0
   */
  @Inject
  Publisher publisher

  /**
   * Builds a template's Docker image
   *
   * @since 0.1.0
   */
  void buildTemplate(Map json) {
    log.info "gathering template's info"
    String id = json.id
    String tag = json.tag

    log.info "unwrapping zip file"
    File baseDir = new File(config.storage.templates, tag)
    ZipUtil.unpack(new File(config.storage.templates, "${tag}.zip"), baseDir)

    log.info "building image ${id} located at $baseDir"
    String imageId = dockerClient
      .buildImageCmd(baseDir)
      .withTag(tag)
      .exec(new BuildImageResultCallback())
      .awaitImageId()

    log.info "successfully built template $tag with id $imageId"
    publisher.publish("templates.image.built", json.payload)
  }

  /**
   * Deletes a template's Docker image
   *
   * @since 0.1.0
   */
  void deleteTemplate(Map json) {
    String tag = json.tag
    log.info "deleting template $tag [docker]"
    dockerClient.removeImageCmd(tag).exec()

    log.info "deleting template $tag [files]"
    Storage.deleteFile(Paths.get(config.storage.templates, "${tag}.zip"))
    Storage.deleteDirectory(Paths.get(config.storage.templates, tag))

    log.info "deleting template $tag [event]"
    publisher.publish("templates.image.deleted", json.payload)
  }
}
