package codes.jun.todo

import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal
import java.util.UUID

@RestController
@RequestMapping("/api/todo-lists")
@Tag(name = "Todo List", description = "Todo List API")
class TodoListController(
    private val todoListService: TodoListService,
    private val todoItemService: TodoItemService
) {
  @GetMapping("/{id}")
  fun getTodoListById(@PathVariable("id") id: UUID, principal: Principal): ResponseEntity<TodoListResponseDto> {
    val list = todoListService.findTodoListById(id) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
    if (list.ownerId != principal.name) {
      return ResponseEntity(HttpStatus.FORBIDDEN)
    }
    val dto = TodoListResponseDto(list.id, list.label, list.createdAt, list.updatedAt)
    return ResponseEntity(dto, HttpStatus.OK)
  }

  @GetMapping()
  fun getTodoLists(principal: Principal): ResponseEntity<List<TodoListResponseDto>> {
    val lists = todoListService.findTodoListsByOwnerId(principal.name)
    val dtos = lists.map { TodoListResponseDto(it.id, it.label, it.createdAt, it.updatedAt) }
    return ResponseEntity(dtos, HttpStatus.OK)
  }

  @PostMapping()
  fun createTodoList(@Valid @RequestBody body: TodoListCreateRequestDto, principal: Principal): ResponseEntity<TodoListResponseDto> {
    val write = TodoListWrite(principal.name, body.label)
    val list = todoListService.createTodoList(write)
    val dto = TodoListResponseDto(list.id, list.label, list.createdAt, list.updatedAt)
    return ResponseEntity(dto, HttpStatus.CREATED)
  }

  @DeleteMapping("/{id}")
  fun deleteTodoListById(@PathVariable("id") id: UUID, principal: Principal): ResponseEntity<TodoListResponseDto> {
    val list = todoListService.findTodoListById(id) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
    if (list.ownerId != principal.name) {
      return ResponseEntity(HttpStatus.FORBIDDEN)
    }
    val deleted = todoListService.deleteTodoListById(id) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
    val dto = TodoListResponseDto(deleted.id, deleted.label, deleted.createdAt, deleted.updatedAt)
    return ResponseEntity(dto, HttpStatus.OK)
  }

  @GetMapping("/{id}/items")
  fun getTodoItemsByListId(@PathVariable("id") id: UUID, principal: Principal): ResponseEntity<List<TodoItemResponseDto>> {
    val list = todoListService.findTodoListById(id) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
    if (list.ownerId != principal.name) {
      return ResponseEntity(HttpStatus.FORBIDDEN)
    }
    val items = todoItemService.findTodoItemsByListId(id)
    val dtos = items.map { TodoItemResponseDto(it.id, it.listId, it.label, it.createdAt, it.updatedAt, it.deletedAt) }
    return ResponseEntity(dtos, HttpStatus.OK)
  }

  @PostMapping("/{id}/items")
  fun createTodoItemByListId(@PathVariable("id") id: UUID, @Valid @RequestBody body: TodoItemCreateRequestDto, principal: Principal): ResponseEntity<TodoItemResponseDto> {
    val list = todoListService.findTodoListById(id) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
    if (list.ownerId != principal.name) {
      return ResponseEntity(HttpStatus.FORBIDDEN)
    }
    val write = TodoItemWrite(id, body.label)
    val item = todoItemService.createTodoItemsByListId(id, write)
    val dto = TodoItemResponseDto(item.id, item.listId, item.label, item.createdAt, item.updatedAt, item.deletedAt)
    return ResponseEntity(dto, HttpStatus.CREATED)
  }

  @DeleteMapping("/{id}/items/{itemId}")
  fun deleteTodoItemByListId(@PathVariable("id") id: UUID, @PathVariable("itemId") itemId: UUID, principal: Principal): ResponseEntity<TodoItemResponseDto> {
    val list = todoListService.findTodoListById(id) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
    if (list.ownerId != principal.name) {
      return ResponseEntity(HttpStatus.FORBIDDEN)
    }
    val item = todoItemService.deleteTodoItemById(itemId) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
    val dto = TodoItemResponseDto(item.id, item.listId, item.label, item.createdAt, item.updatedAt, item.deletedAt)
    return ResponseEntity(dto, HttpStatus.OK)
  }

  @GetMapping("/{id}/deleted-items")
  fun getDeletedItemsByListId(@PathVariable("id") id: UUID, principal: Principal): ResponseEntity<List<TodoItemResponseDto>> {
    val list = todoListService.findTodoListById(id) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
    if (list.ownerId != principal.name) {
      return ResponseEntity(HttpStatus.FORBIDDEN)
    }
    val items = todoItemService.findDeletedTodoItemsByListId(id)
    val dtos = items.map { TodoItemResponseDto(it.id, it.listId, it.label, it.createdAt, it.updatedAt, it.deletedAt) }
    return ResponseEntity(dtos, HttpStatus.OK)
  }

  @PatchMapping("/{id}/items/{itemId}")
  fun updateTodoItemById(@PathVariable("id") id: UUID, @PathVariable("itemId") itemId: UUID, @Valid @RequestBody body: TodoItemCreateRequestDto, principal: Principal): ResponseEntity<TodoItemResponseDto> {
    val list = todoListService.findTodoListById(id) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
    if (list.ownerId != principal.name) {
      return ResponseEntity(HttpStatus.FORBIDDEN)
    }
    val write = TodoItemWrite(id, body.label)
    val item = todoItemService.updateTodoItemById(itemId, write)
    val dto = TodoItemResponseDto(item.id, item.listId, item.label, item.createdAt, item.updatedAt, item.deletedAt)
    return ResponseEntity(dto, HttpStatus.OK)
  }
}
