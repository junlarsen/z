package codes.jun.todo

import org.springframework.stereotype.Service
import java.util.UUID

@Service
class TodoListService(private val todoListRepository: TodoListRepository) {
  fun findTodoListById(id: UUID): TodoList? {
    return todoListRepository.findTodoListById(id)
  }

  fun findTodoListsByOwnerId(id: String): List<TodoList> {
    return todoListRepository.findTodoListsByOwnerId(id)
  }

  fun updateTodoListById(id: UUID, input: TodoListWrite): TodoList {
    return todoListRepository.updateTodoListById(id, input)
  }

  fun deleteTodoListById(id: UUID): TodoList? {
    return todoListRepository.deleteTodoListById(id)
  }

  fun createTodoList(input: TodoListWrite): TodoList {
    val match = todoListRepository.findTodoListByLabelAndOwnerId(input.label, input.ownerId)
    if (match != null) {
      throw TodoListAlreadyExistsException(input.label, match.id)
    }
    return todoListRepository.createTodoList(input)
  }
}
