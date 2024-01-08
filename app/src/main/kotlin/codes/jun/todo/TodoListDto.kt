package codes.jun.todo

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.time.OffsetDateTime
import java.util.UUID

data class TodoListResponseDto(
    val id: UUID,
    val label: String,
    val createdAt: OffsetDateTime,
    val updatedAt: OffsetDateTime
)

data class TodoListCreateRequestDto(
    @field:NotNull @field:NotBlank val label: String
)
