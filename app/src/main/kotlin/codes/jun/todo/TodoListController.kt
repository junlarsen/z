package codes.jun.todo

import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/api/todo-list")
class TodoListController(private val todoListService: TodoListService) {
  @GetMapping("/{id}")
  fun get(@PathVariable("id") id: UUID, @AuthenticationPrincipal user: OAuth2User): ResponseEntity<TodoListResponseDto> {
    val list = todoListService.findTodoListById(id) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
    if (list.ownerId != user.name) {
      return ResponseEntity(HttpStatus.FORBIDDEN)
    }
    val dto = TodoListResponseDto(list.id, list.label, list.createdAt, list.updatedAt)
    return ResponseEntity(dto, HttpStatus.OK)
  }

  @GetMapping()
  fun get(@AuthenticationPrincipal user: OAuth2User): ResponseEntity<List<TodoListResponseDto>> {
    val lists = todoListService.findTodoListsByOwnerId(user.name)
    val dtos = lists.map { TodoListResponseDto(it.id, it.label, it.createdAt, it.updatedAt) }
    return ResponseEntity(dtos, HttpStatus.OK)
  }

  @PostMapping()
  fun post(@Valid @RequestBody body: TodoListCreateRequestDto, @AuthenticationPrincipal user: OAuth2User): ResponseEntity<TodoListResponseDto> {
    val write = TodoListWrite(user.name, body.label)
    val list = todoListService.createTodoList(write)
    val dto = TodoListResponseDto(list.id, list.label, list.createdAt, list.updatedAt)
    return ResponseEntity(dto, HttpStatus.CREATED)
  }
}
