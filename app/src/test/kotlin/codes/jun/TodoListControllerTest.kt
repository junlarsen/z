package codes.jun

import codes.jun.todo.TodoItemCreateRequestDto
import codes.jun.todo.TodoItemService
import codes.jun.todo.TodoItemWrite
import codes.jun.todo.TodoListCreateRequestDto
import codes.jun.todo.TodoListService
import codes.jun.todo.TodoListWrite
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.delete
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.patch
import org.springframework.test.web.servlet.post

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class TodoListControllerTest {
  @Autowired
  private lateinit var mockMvc: MockMvc

  @Autowired
  private lateinit var objectMapper: ObjectMapper

  @Autowired
  private lateinit var todoListService: TodoListService

  @Autowired
  private lateinit var todoItemService: TodoItemService

  @Test
  fun `should create todo lists`() {
    val dto = TodoListCreateRequestDto("Groceries")
    val json = objectMapper.writeValueAsString(dto)
    mockMvc.post("/api/todo-lists") {
      with(jwt())
      contentType = MediaType.APPLICATION_JSON
      content = json
    }.andExpect {
      status { isCreated() }
      jsonPath("$.id") { isNotEmpty() }
      jsonPath("$.label") { value("Groceries") }
      jsonPath("$.createdAt") { isNotEmpty() }
      jsonPath("$.updatedAt") { isNotEmpty() }
    }
  }

  @Test
  fun `should fail on duplicate list name for user`() {
    val dto = TodoListCreateRequestDto("MyGroceries")
    val json = objectMapper.writeValueAsString(dto)
    mockMvc.post("/api/todo-lists") {
      with(jwt())
      contentType = MediaType.APPLICATION_JSON
      content = json
    }.andExpect {
      status { isCreated() }
    }

    mockMvc.post("/api/todo-lists") {
      with(jwt())
      contentType = MediaType.APPLICATION_JSON
      content = json
    }.andExpect {
      status { isConflict() }
    }
  }

  @Test
  fun `should be able to view todo list`() {
    val input = TodoListWrite("user", "Food")
    val list = todoListService.createTodoList(input)

    mockMvc.get("/api/todo-lists/${list.id}") {
      with(jwt())
      accept = MediaType.APPLICATION_JSON
    }.andExpect {
      status { isOk() }
      jsonPath("$.id") { value(list.id.toString()) }
      jsonPath("$.label") { value("Food") }
      jsonPath("$.createdAt") { isNotEmpty() }
      jsonPath("$.updatedAt") { isNotEmpty() }
    }
  }

  @Test
  fun `should not be able to view lists made by others`() {
    val input = TodoListWrite("my-other-user", "Clothes")
    val list = todoListService.createTodoList(input)

    mockMvc.get("/api/todo-lists/${list.id}") {
      with(jwt())
      accept = MediaType.APPLICATION_JSON
    }.andExpect {
      status { isForbidden() }
    }
  }

  @Test
  fun `should view all lists for a given user`() {
    val input = TodoListWrite("user2", "Food")
    todoListService.createTodoList(input)

    mockMvc.get("/api/todo-lists") {
      with(jwt().jwt {
        it.claim("sub", "user2")
      })
      accept = MediaType.APPLICATION_JSON
    }.andExpect {
      status { isOk() }
      jsonPath("$.length()") { value(1) }
    }
  }

  @Test
  fun `should be able to delete todo list for a given user`() {
    val input = TodoListWrite("user", "DeleteThisList")
    val list = todoListService.createTodoList(input)

    mockMvc.delete("/api/todo-lists/${list.id}") {
      with(jwt())
      accept = MediaType.APPLICATION_JSON
    }.andExpect {
      status { isOk() }
      jsonPath("$.id") { value(list.id.toString()) }
      jsonPath("$.label") { value("DeleteThisList") }
      jsonPath("$.createdAt") { isNotEmpty() }
      jsonPath("$.updatedAt") { isNotEmpty() }
    }
  }

  @Test
  fun `should not be able to delete lists owned by other users`() {
    val input = TodoListWrite("my-other-user", "CantTouchThisList")
    val list = todoListService.createTodoList(input)

    mockMvc.delete("/api/todo-lists/${list.id}") {
      with(jwt())
      accept = MediaType.APPLICATION_JSON
    }.andExpect {
      status { isForbidden() }
    }
  }

  @Test
  fun `should be able to create an item in a list`() {
    val input = TodoListWrite("user", "create-item-list")
    val list = todoListService.createTodoList(input)

    val dto = TodoItemCreateRequestDto(list.id, "Milk")
    val json = objectMapper.writeValueAsString(dto)

    mockMvc.post("/api/todo-lists/${list.id}/items") {
      with(jwt())
      contentType = MediaType.APPLICATION_JSON
      content = json
    }.andExpect {
      status { isCreated() }
      jsonPath("$.id") { isNotEmpty() }
      jsonPath("$.listId") { value(list.id.toString()) }
      jsonPath("$.label") { value("Milk") }
      jsonPath("$.createdAt") { isNotEmpty() }
      jsonPath("$.updatedAt") { isNotEmpty() }
    }
  }

  @Test
  fun `should be able to fetch items from a list`() {
    val input = TodoListWrite("user", "fetch-items-list")
    val list = todoListService.createTodoList(input)

    val itemInput = TodoItemWrite(list.id, "Milk")
    val item = todoItemService.createTodoItemsByListId(list.id, itemInput)

    mockMvc.get("/api/todo-lists/${list.id}/items") {
      with(jwt())
      accept = MediaType.APPLICATION_JSON
    }.andExpect {
      status { isOk() }
      jsonPath("$.length()") { value(1) }
      jsonPath("$[0].id") { value(item.id.toString()) }
    }
  }

  @Test
  fun `should be able to update an item in a list`() {
    val input = TodoListWrite("user", "update-item-list")
    val list = todoListService.createTodoList(input)

    val itemInput = TodoItemWrite(list.id, "Milk")
    val item = todoItemService.createTodoItemsByListId(list.id, itemInput)

    val dto = TodoItemCreateRequestDto(list.id, "Milk and Honey")
    val json = objectMapper.writeValueAsString(dto)

    mockMvc.patch("/api/todo-lists/${list.id}/items/${item.id}") {
      with(jwt())
      contentType = MediaType.APPLICATION_JSON
      content = json
    }.andExpect {
      status { isOk() }
      jsonPath("$.id") { value(item.id.toString()) }
      jsonPath("$.listId") { value(list.id.toString()) }
      jsonPath("$.label") { value("Milk and Honey") }
      jsonPath("$.createdAt") { isNotEmpty() }
      jsonPath("$.updatedAt") { isNotEmpty() }
    }
  }

  @Test
  fun `should return not found on write operations if item does not exist in list`() {
    val input = TodoListWrite("user", "update-item-list-should-fail")
    val list = todoListService.createTodoList(input)

    val dto = TodoItemCreateRequestDto(list.id, "Milk and Honey")
    val json = objectMapper.writeValueAsString(dto)

    mockMvc.patch("/api/todo-lists/${list.id}/items/00000000-0000-0000-0000-000000000000") {
      with(jwt())
      contentType = MediaType.APPLICATION_JSON
      content = json
    }.andExpect {
      status { isNotFound() }
    }
  }

  @Test
  fun `should be able to delete items from a list`() {
    val input = TodoListWrite("user", "delete-item-list")
    val list = todoListService.createTodoList(input)

    val itemInput = TodoItemWrite(list.id, "Milk")
    val item = todoItemService.createTodoItemsByListId(list.id, itemInput)

    mockMvc.delete("/api/todo-lists/${list.id}/items/${item.id}") {
      with(jwt())
      accept = MediaType.APPLICATION_JSON
    }.andExpect {
      status { isOk() }
      jsonPath("$.id") { value(item.id.toString()) }
      jsonPath("$.listId") { value(list.id.toString()) }
      jsonPath("$.label") { value("Milk") }
      jsonPath("$.createdAt") { isNotEmpty() }
      jsonPath("$.updatedAt") { isNotEmpty() }
    }
  }

  @Test
  fun `should be able to fetch deleted items from a list`() {
    val input = TodoListWrite("user", "fetch-deleted-items-list")
    val list = todoListService.createTodoList(input)

    val itemInput = TodoItemWrite(list.id, "Milk")
    val item = todoItemService.createTodoItemsByListId(list.id, itemInput)
    todoItemService.deleteTodoItemById(item.id)

    mockMvc.get("/api/todo-lists/${list.id}/deleted-items") {
      with(jwt())
      accept = MediaType.APPLICATION_JSON
    }.andExpect {
      status { isOk() }
      jsonPath("$.length()") { value(1) }
      jsonPath("$[0].id") { value(item.id.toString()) }
    }
  }
}
