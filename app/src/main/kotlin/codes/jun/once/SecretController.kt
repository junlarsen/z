package codes.jun.once

import org.apache.logging.log4j.LogManager
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController()
@RequestMapping("/api/secret")
class SecretController(val secretService: SecretService) {
  private val logger = LogManager.getLogger(this::class.java)

  @GetMapping("/{id}")
  fun get(@PathVariable("id") id: UUID): ResponseEntity<String> {
    return ResponseEntity("Hello $id", HttpStatus.OK)
  }
}
