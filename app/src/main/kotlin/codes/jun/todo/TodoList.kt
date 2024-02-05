package codes.jun.todo

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.time.OffsetDateTime
import java.util.UUID
import migrations.Todo_lists as TodoLists

data class TodoList(
    val id: UUID,
    val ownerId: String,
    val label: String,
    val createdAt: OffsetDateTime,
    val updatedAt: OffsetDateTime,
)

data class TodoListWrite(
    val ownerId: String,
    val label: String,
)

@ResponseStatus(HttpStatus.NOT_FOUND)
class TodoListNotFoundException(id: UUID) : RuntimeException("TodoList with id $id not found")

@ResponseStatus(HttpStatus.CONFLICT)
class TodoListAlreadyExistsException(label: String, id: UUID) : RuntimeException("TodoList with name $label already exists with id $id")

fun mapToTodoList(todoList: TodoLists): TodoList = TodoList(
    id = todoList.id,
    ownerId = todoList.owner_id,
    label = todoList.label,
    createdAt = todoList.created_at,
    updatedAt = todoList.updated_at,
)
