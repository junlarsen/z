package codes.jun.todo

import codes.jun.zdatabase.TodoListItemQueries
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
class TodoItemRepository(private val todoListItemQueries: TodoListItemQueries) {
  fun findTodoItemsByListId(listId: UUID): List<TodoItem> {
    return todoListItemQueries.findTodoItemsByListId(listId).executeAsList().map(::mapToTodoItem)
  }

  fun findTodoItemById(id: UUID): TodoItem? {
    val item = todoListItemQueries.findTodoItemById(id).executeAsOneOrNull()
    return item?.let(::mapToTodoItem)
  }

  fun findDeletedTodoItemsByListId(listId: UUID): List<TodoItem> {
    return todoListItemQueries.findDeletedTodoItemsByListId(listId).executeAsList().map {
      TodoItem(
          id = it.id,
          listId = it.list_id,
          label = it.label,
          createdAt = it.created_at,
          updatedAt = it.updated_at,
          deletedAt = it.deleted_at,
      )
    }
  }

  fun updateTodoItemById(id: UUID, input: TodoItemWrite): TodoItem {
    val item = todoListItemQueries.updateTodoItemById(input.label, id).executeAsOne()
    return mapToTodoItem(item)
  }

  fun deleteTodoItemById(id: UUID): TodoItem? {
    val item = todoListItemQueries.deleteTodoItemById(id).executeAsOneOrNull()
    return item?.let(::mapToTodoItem)
  }

  fun createTodoItemsByListId(listId: UUID, input: TodoItemWrite): TodoItem {
    val item = todoListItemQueries.createTodoItemByListId(
        list_id = listId,
        label = input.label,
    ).executeAsOne()
    return mapToTodoItem(item)
  }
}
