package codes.jun

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
import org.springframework.test.web.servlet.get
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

  @Test
  fun `should create todo lists`() {
    val dto = TodoListCreateRequestDto("Groceries")
    val json = objectMapper.writeValueAsString(dto)
    mockMvc.post("/api/todo-list") {
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
    mockMvc.post("/api/todo-list") {
      with(jwt())
      contentType = MediaType.APPLICATION_JSON
      content = json
    }.andExpect {
      status { isCreated() }
    }

    mockMvc.post("/api/todo-list") {
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

    mockMvc.get("/api/todo-list/${list.id}") {
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

    mockMvc.get("/api/todo-list/${list.id}") {
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

    mockMvc.get("/api/todo-list") {
      with(jwt().jwt {
        it.claim("sub", "user2")
      })
      accept = MediaType.APPLICATION_JSON
    }.andExpect {
      status { isOk() }
      jsonPath("$.length()") { value(1) }
    }
  }
}
