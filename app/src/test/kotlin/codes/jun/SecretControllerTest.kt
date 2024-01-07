package codes.jun

import codes.jun.once.SecretCreateRequestDto
import codes.jun.once.SecretService
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
import java.time.OffsetDateTime

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class SecretControllerTest {
  @Autowired
  private lateinit var mockMvc: MockMvc

  @Autowired
  private lateinit var objectMapper: ObjectMapper

  @Test
  fun `should create one time secrets`() {
    val dto = SecretCreateRequestDto("hunter2", OffsetDateTime.now().plusHours(12), 1)
    val json = objectMapper.writeValueAsString(dto)
    mockMvc.post("/api/secret") {
      contentType = MediaType.APPLICATION_JSON
      content = json
    }.andExpect {
      status { isCreated() }
      jsonPath("$.id") { isNotEmpty() }
      jsonPath("$.secret") { value("hunter2") }
      jsonPath("$.expiresAt") { isNotEmpty() }
      jsonPath("$.remainingViews") { value(1) }
    }
  }
}