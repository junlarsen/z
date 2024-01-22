package codes.jun.once

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
import java.util.UUID

@RestController()
@RequestMapping("/api/secret")
class SecretController(private val secretService: SecretService) {
  @GetMapping("/{id}")
  fun get(@PathVariable("id") id: String): ResponseEntity<SecretResponseDto> {
    val secret = secretService.findSecretBySlug(id) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
    val dto = SecretResponseDto(secret.id, secret.secret, secret.expiresAt, secret.slug)
    secretService.reduceRemainingViewsById(secret.id)
    return ResponseEntity(dto, HttpStatus.OK)
  }

  @GetMapping("/{id}/preview")
  fun preview(@PathVariable("id") id: String): ResponseEntity<SecretPreviewResponseDto> {
    val secret = secretService.findSecretBySlug(id) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
    val dto = SecretPreviewResponseDto(secret.id, secret.expiresAt)
    return ResponseEntity(dto, HttpStatus.OK)
  }

  @PostMapping()
  fun post(@Valid @RequestBody input: SecretCreateRequestDto): ResponseEntity<SecretResponseDto> {
    val slug = secretService.createSlug()
    val write = SecretWrite(input.secret, input.expiresAt, input.remainingViews, slug)
    val secret = secretService.createSecret(write)
    val dto = SecretResponseDto(secret.id, secret.secret, secret.expiresAt, slug)
    return ResponseEntity(dto, HttpStatus.CREATED)
  }

  @DeleteMapping("/{id}")
  fun delete(@PathVariable("id") id: UUID): ResponseEntity<UUID> {
    secretService.deleteSecretById(id)
    return ResponseEntity(id, HttpStatus.OK)
  }
}
