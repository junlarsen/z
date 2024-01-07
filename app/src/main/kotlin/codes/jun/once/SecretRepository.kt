package codes.jun.once

import codes.jun.pivot.SecretQueries
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
class SecretRepository(private val secretQueries: SecretQueries) {
  fun findSecretById(id: UUID): Secret? {
    val secret = secretQueries.findSecretById(id).executeAsOneOrNull()
    return secret?.let(::mapToSecret) ?: return null
  }

  fun deleteSecretById(id: UUID) {
    secretQueries.deleteSecretById(id)
  }

  fun updateSecretById(input: Secret): Secret {
    val secret = secretQueries.updateSecretById(
        id = input.id,
        secret = input.secret,
        expires_at = input.expiresAt,
        remaining_views = input.remainingViews,
    ).executeAsOne()
    return mapToSecret(secret)
  }

  fun createSecret(input: SecretWrite): Secret {
    val secret = secretQueries.createSecret(
        secret = input.secret,
        expires_at = input.expiresAt,
        remaining_views = input.remainingViews,
    ).executeAsOne()
    return mapToSecret(secret)
  }
}
