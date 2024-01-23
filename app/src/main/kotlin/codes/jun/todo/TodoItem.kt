package codes.jun.todo

import java.time.OffsetDateTime
import java.util.UUID
import migrations.Todo_items as TodoItems

data class TodoItem(
    val id: UUID,
    val listId: UUID,
    val label: String,
    val createdAt: OffsetDateTime,
    val updatedAt: OffsetDateTime,
    val deletedAt: OffsetDateTime?,
)

data class TodoItemWrite(
    val listId: UUID,
    val label: String,
)

class TodoItemNotFoundException(id: UUID) : RuntimeException("TodoItem with id $id not found")

fun mapToTodoItem(todoItem: TodoItems): TodoItem = TodoItem(
    id = todoItem.id,
    listId = todoItem.list_id,
    label = todoItem.label,
    createdAt = todoItem.created_at,
    updatedAt = todoItem.updated_at,
    deletedAt = todoItem.deleted_at,
)
