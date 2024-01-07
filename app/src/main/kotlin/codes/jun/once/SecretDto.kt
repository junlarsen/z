package codes.jun.once

import java.time.OffsetDateTime
import java.util.UUID

data class SecretDto(
    val id: UUID,
    val secret: String,
    val expiresAt: OffsetDateTime
)

data class SecretPreviewDto(
    val id: UUID,
    val expiresAt: OffsetDateTime
)
