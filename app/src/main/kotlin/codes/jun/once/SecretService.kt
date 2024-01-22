package codes.jun.once

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.stereotype.Service
import java.time.OffsetDateTime
import java.util.UUID

@Service
class SecretService(private val secretRepository: SecretRepository) {
  private val logger: Logger = LogManager.getLogger(this::class.java)

  fun findSecretById(id: UUID): Secret? {
    return secretRepository.findSecretById(id)?.let {
      if (it.expiresAt.isBefore(OffsetDateTime.now())) {
        secretRepository.deleteSecretById(id)
        null
      } else {
        it
      }
    }
  }

  fun findSecretBySlug(slug: String): Secret? {
    return secretRepository.findSecretBySlug(slug)?.let {
      if (it.expiresAt.isBefore(OffsetDateTime.now())) {
        secretRepository.deleteSecretById(it.id)
        null
      } else {
        it
      }
    }
  }

  fun deleteSecretById(id: UUID) {
    secretRepository.deleteSecretById(id)
  }

  fun createSecret(input: SecretWrite): Secret {
    return secretRepository.createSecret(input)
  }

  fun reduceRemainingViewsById(id: UUID): Secret {
    val secret = secretRepository.findSecretById(id) ?: throw SecretNotFoundException(id)
    if (secret.remainingViews <= 0) {
      throw IllegalStateException("Tried to reduce remaining views of secret with id $id, but it has no remaining views")
    }
    logger.info("Reducing remaining views of secret with id $id from ${secret.remainingViews} to ${secret.remainingViews - 1}")
    if (secret.remainingViews == 1) {
      logger.info("Deleting secret with id $id because it has no remaining views")
      return secretRepository.deleteSecretById(id)
          ?: throw IllegalStateException("Tried to delete secret with id $id, but it was already deleted")
    }
    val input = SecretWrite(secret.secret, secret.expiresAt, secret.remainingViews - 1, secret.slug)
    return secretRepository.updateSecretById(secret.id, input)
  }

  fun createSlug(): String {
    val range = ('0'..'9') + ('a'..'z') + ('A'..'Z')
    fun getSlug() = (1..8)
        .map { range.random() }
        .joinToString("")

    var slug = getSlug()
    while (findSecretBySlug(slug) != null) {
      slug = getSlug()
    }
    return slug
  }
}
