package codes.jun.once

import codes.jun.pivot.Secrets
import java.time.OffsetDateTime
import java.util.UUID

data class Secret(
    val id: UUID,
    val secret: String,
    val createdAt: OffsetDateTime,
    val expiresAt: OffsetDateTime,
    val remainingViews: Int,
)

data class SecretWrite(
    val secret: String,
    val expiresAt: OffsetDateTime,
    val remainingViews: Int,
)

class SecretNotFoundException(id: UUID) : RuntimeException("Secret with id $id not found")

fun mapToSecret(secret: Secrets): Secret = Secret(
    id = secret.id,
    secret = secret.secret,
    createdAt = secret.created_at,
    expiresAt = secret.expires_at,
    remainingViews = secret.remaining_views,
)
