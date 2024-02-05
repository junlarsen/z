package codes.jun.todo

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.time.OffsetDateTime
import java.util.UUID

data class TodoItemResponseDto(
    val id: UUID,
    val listId: UUID,
    val label: String,
    val createdAt: OffsetDateTime,
    val updatedAt: OffsetDateTime,
    val deletedAt: OffsetDateTime?
)

data class TodoItemCreateRequestDto(
    @field:NotNull val listId: UUID,
    @field:NotNull @field:NotBlank val label: String
)