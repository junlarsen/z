package codes.jun.once

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.OffsetDateTime
import java.util.UUID

@RestController()
@RequestMapping("/api/secret")
class SecretController(private val secretService: SecretService) {
  @GetMapping("/{id}")
  fun get(@PathVariable("id") id: UUID): ResponseEntity<SecretDto> {
    val secret = secretService.findSecretById(id) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
    val dto = SecretDto(secret.id, secret.secret, secret.expiresAt)
    return ResponseEntity(dto, HttpStatus.OK)
  }

  @GetMapping("/{id}/preview")
  fun preview(@PathVariable("id") id: UUID): ResponseEntity<SecretPreviewDto?> {
    val secret = secretService.findSecretById(id) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
    val dto = SecretPreviewDto(secret.id, secret.expiresAt)
    return ResponseEntity(dto, HttpStatus.OK)
  }

  @PostMapping()
  fun post(): ResponseEntity<SecretDto> {
    val secret = secretService.createSecret(SecretWrite("Hello world", OffsetDateTime.now(), 1))
    val dto = SecretDto(secret.id, secret.secret, secret.expiresAt)
    return ResponseEntity(dto, HttpStatus.CREATED)
  }

  @DeleteMapping("/{id}")
  fun delete(@PathVariable("id") id: UUID): ResponseEntity<UUID> {
    secretService.deleteSecretById(id)
    return ResponseEntity(id, HttpStatus.OK)
  }
}
