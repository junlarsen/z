package codes.jun

import codes.jun.once.SecretCreateRequestDto
import codes.jun.once.SecretService
import codes.jun.once.SecretWrite
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.delete
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import java.time.OffsetDateTime
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class SecretControllerTest {
  @Autowired
  private lateinit var mockMvc: MockMvc

  @Autowired
  private lateinit var objectMapper: ObjectMapper

  @Autowired
  private lateinit var secretService: SecretService

  @Test
  fun `should create one time secrets`() {
    val dto = SecretCreateRequestDto("hunter2", OffsetDateTime.now().plusHours(12), 1)
    val json = objectMapper.writeValueAsString(dto)
    mockMvc.post("/api/secret") {
      contentType = MediaType.APPLICATION_JSON
      content = json
      with(jwt())
    }.andExpect {
      status { isCreated() }
      jsonPath("$.id") { isNotEmpty() }
      jsonPath("$.secret") { value("hunter2") }
      jsonPath("$.expiresAt") { isNotEmpty() }
    }
  }

  @Test
  fun `viewing secret should invalidate it`() {
    val input = SecretWrite("hunter2", OffsetDateTime.now().plusHours(12), 1, "slug")
    val secret = secretService.createSecret(input)

    mockMvc.get("/api/secret/${secret.slug}") {
      accept = MediaType.APPLICATION_JSON
      with(jwt())
    }.andExpect {
      status { isOk() }
      jsonPath("$.id") { value(secret.id.toString()) }
      jsonPath("$.secret") { value("hunter2") }
      jsonPath("$.expiresAt") { value(secret.expiresAt.toString()) }
    }

    mockMvc.get("/api/secret/${secret.slug}") {
      accept = MediaType.APPLICATION_JSON
      with(jwt())
    }.andExpect {
      status { isNotFound() }
    }
  }

  @Test
  fun `previewing does not consume a view`() {
    val input = SecretWrite("hunter2", OffsetDateTime.now().plusHours(12), 1, "zzzqw")
    val secret = secretService.createSecret(input)

    mockMvc.get("/api/secret/${secret.slug}/preview") {
      accept = MediaType.APPLICATION_JSON
      with(jwt())
    }.andExpect {
      status { isOk() }
      jsonPath("$.id") { value(secret.id.toString()) }
      jsonPath("$.expiresAt") { value(secret.expiresAt.toString()) }
      jsonPath("$.secret") { doesNotExist() }
    }

    val secretAfterPreview = secretService.findSecretById(secret.id)
    assertNotNull(secretAfterPreview)
    assertEquals(1, secretAfterPreview.remainingViews)
  }

  @Test
  fun `deleting a secret permanently removes it`() {
    val input = SecretWrite("hunter2", OffsetDateTime.now().plusHours(12), 1, "zzzz")
    val secret = secretService.createSecret(input)

    mockMvc.delete("/api/secret/${secret.slug}") {
      accept = MediaType.APPLICATION_JSON
      with(jwt())
    }.andExpect {
      status { isOk() }
    }

    val secretAfterDelete = secretService.findSecretById(secret.id)
    assertNull(secretAfterDelete)
  }
}