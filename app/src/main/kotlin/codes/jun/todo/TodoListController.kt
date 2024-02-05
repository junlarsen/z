package codes.jun.todo

import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal
import java.util.UUID

@RestController
@RequestMapping("/api/todo-lists")
class TodoListController(private val todoListService: TodoListService) {
  @GetMapping("/{id}")
  fun get(@PathVariable("id") id: UUID, principal: Principal): ResponseEntity<TodoListResponseDto> {
    val list = todoListService.findTodoListById(id) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
    if (list.ownerId != principal.name) {
      return ResponseEntity(HttpStatus.FORBIDDEN)
    }
    val dto = TodoListResponseDto(list.id, list.label, list.createdAt, list.updatedAt)
    return ResponseEntity(dto, HttpStatus.OK)
  }

  @GetMapping()
  fun get(principal: Principal): ResponseEntity<List<TodoListResponseDto>> {
    val lists = todoListService.findTodoListsByOwnerId(principal.name)
    val dtos = lists.map { TodoListResponseDto(it.id, it.label, it.createdAt, it.updatedAt) }
    return ResponseEntity(dtos, HttpStatus.OK)
  }

  @PostMapping()
  fun post(@Valid @RequestBody body: TodoListCreateRequestDto, principal: Principal): ResponseEntity<TodoListResponseDto> {
    val write = TodoListWrite(principal.name, body.label)
    val list = todoListService.createTodoList(write)
    val dto = TodoListResponseDto(list.id, list.label, list.createdAt, list.updatedAt)
    return ResponseEntity(dto, HttpStatus.CREATED)
  }

  @DeleteMapping("/{id}")
  fun delete(@PathVariable("id") id: UUID, principal: Principal): ResponseEntity<TodoListResponseDto> {
    val list = todoListService.findTodoListById(id) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
    if (list.ownerId != principal.name) {
      return ResponseEntity(HttpStatus.FORBIDDEN)
    }
    val deleted = todoListService.deleteTodoListById(id) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
    val dto = TodoListResponseDto(deleted.id, deleted.label, deleted.createdAt, deleted.updatedAt)
    return ResponseEntity(dto, HttpStatus.OK)
  }
}
