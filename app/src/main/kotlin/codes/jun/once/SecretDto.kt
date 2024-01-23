package codes.jun.once

import jakarta.validation.constraints.Future
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import java.time.OffsetDateTime
import java.util.UUID

data class SecretResponseDto(
    val id: UUID,
    val secret: String,
    val expiresAt: OffsetDateTime,
    val slug: String
)

data class SecretPreviewResponseDto(
    val id: UUID,
    val expiresAt: OffsetDateTime
)

data class SecretCreateRequestDto(
    @field:NotNull val secret: String,
    @field:NotNull @field:Future val expiresAt: OffsetDateTime,
    @field:NotNull @field:Positive val remainingViews: Int,
)
