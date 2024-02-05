package codes.jun.todo

import org.springframework.stereotype.Service
import java.util.UUID

@Service
class TodoItemService(private val todoItemRepository: TodoItemRepository) {
  fun findTodoItemsByListId(listId: UUID): List<TodoItem> {
    return todoItemRepository.findTodoItemsByListId(listId)
  }

  fun findDeletedTodoItemsByListId(listId: UUID): List<TodoItem> {
    return todoItemRepository.findDeletedTodoItemsByListId(listId)
  }

  fun updateTodoItemById(id: UUID, input: TodoItemWrite): TodoItem {
    val item = todoItemRepository.findTodoItemById(id) ?: throw TodoItemNotFoundException(id)
    return todoItemRepository.updateTodoItemById(item.id, input)
  }

  fun deleteTodoItemById(id: UUID): TodoItem? {
    return todoItemRepository.deleteTodoItemById(id)
  }

  fun createTodoItemsByListId(listId: UUID, input: TodoItemWrite): TodoItem {
    return todoItemRepository.createTodoItemsByListId(listId, input)
  }
}
