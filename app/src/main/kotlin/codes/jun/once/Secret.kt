package codes.jun.once

import migrations.Secrets
import java.time.OffsetDateTime
import java.util.UUID

data class Secret(
    val id: UUID,
    val secret: String,
    val createdAt: OffsetDateTime,
    val expiresAt: OffsetDateTime,
    val remainingViews: Int,
    val slug: String
)

data class SecretWrite(
    val secret: String,
    val expiresAt: OffsetDateTime,
    val remainingViews: Int,
    val slug: String
)

class SecretNotFoundException(id: UUID) : RuntimeException("Secret with id $id not found")

fun mapToSecret(secret: Secrets): Secret = Secret(
    id = secret.id,
    secret = secret.secret,
    createdAt = secret.created_at,
    expiresAt = secret.expires_at,
    remainingViews = secret.remaining_views,
    slug = secret.slug
)
