package codes.jun.todo

import codes.jun.zdatabase.TodoListQueries
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
class TodoListRepository(private val todoListQueries: TodoListQueries) {
  fun findTodoListById(id: UUID): TodoList? {
    val list = todoListQueries.findTodoListById(id).executeAsOneOrNull()
    return list?.let(::mapToTodoList)
  }

  fun findTodoListByLabelAndOwnerId(label: String, id: String): TodoList? {
    val list = todoListQueries.findTodoListByLabelAndOwnerId(label, id).executeAsOneOrNull()
    return list?.let(::mapToTodoList)
  }

  fun findTodoListsByOwnerId(id: String): List<TodoList> {
    val lists = todoListQueries.findTodoListsByOwnerId(id).executeAsList()
    return lists.map(::mapToTodoList)
  }

  fun updateTodoListById(id: UUID, input: TodoListWrite): TodoList {
    val list = todoListQueries.updateTodoListById(
        id = id,
        label = input.label,
        owner_id = input.ownerId
    ).executeAsOne()
    return mapToTodoList(list)
  }

  fun deleteTodoListById(id: UUID): TodoList? {
    val list = todoListQueries.deleteTodoListById(id).executeAsOneOrNull()
    return list?.let(::mapToTodoList)
  }

  fun createTodoList(input: TodoListWrite): TodoList {
    val list = todoListQueries.createTodoList(
        owner_id = input.ownerId,
        label = input.label,
    ).executeAsOne()
    return mapToTodoList(list)
  }
}
