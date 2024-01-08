package codes.jun.once

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class SecretService(private val secretRepository: SecretRepository) {
  private val logger: Logger = LogManager.getLogger(this::class.java)

  fun findSecretById(id: UUID): Secret? {
    return secretRepository.findSecretById(id)?.let {
      if (it.remainingViews <= 0) {
        logger.info("Deleting secret with id $id because it has no remaining views")
        secretRepository.deleteSecretById(id)
        return@let null
      }
      it
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
    val input = SecretWrite(secret.secret, secret.expiresAt, secret.remainingViews - 1);
    return secretRepository.updateSecretById(secret.id, input)
  }
}
