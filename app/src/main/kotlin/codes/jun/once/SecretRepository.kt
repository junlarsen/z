package codes.jun.once

import codes.jun.pivot.SecretQueries
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
class SecretRepository(private val secretQueries: SecretQueries) {
  fun findSecretById(id: UUID): Secret? {
    val secret = secretQueries.findSecretById(id).executeAsOneOrNull()
    return secret?.let(::mapToSecret)
  }

  fun findSecretBySlug(slug: String): Secret? {
    val secret = secretQueries.findSecretBySlug(slug).executeAsOneOrNull()
    return secret?.let(::mapToSecret)
  }

  fun deleteSecretById(id: UUID): Secret? {
    val secret = secretQueries.deleteSecretById(id).executeAsOneOrNull()
    return secret?.let(::mapToSecret)
  }

  fun updateSecretById(id: UUID, input: SecretWrite): Secret {
    val secret = secretQueries.updateSecretById(
        id = id,
        secret = input.secret,
        expires_at = input.expiresAt,
        remaining_views = input.remainingViews,
        slug = input.slug,
    ).executeAsOne()
    return mapToSecret(secret)
  }

  fun createSecret(input: SecretWrite): Secret {
    val secret = secretQueries.createSecret(
        secret = input.secret,
        expires_at = input.expiresAt,
        remaining_views = input.remainingViews,
        slug = input.slug,
    ).executeAsOne()
    return mapToSecret(secret)
  }
}
