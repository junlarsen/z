package codes.jun.user

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/user")
class UserController {
  @RequestMapping("/me")
  fun me(): ResponseEntity<UserResponseDto> {
    val authentication = SecurityContextHolder.getContext().authentication
    if (authentication == null || !authentication.isAuthenticated) {
      return ResponseEntity(HttpStatus.UNAUTHORIZED)
    }
    val principal = authentication.principal as DefaultOidcUser
    val dto =
      UserResponseDto(
        sub = principal.name,
        name = principal.attributes["name"] as String,
        imageUrl = principal.attributes["picture"] as String?,
      )
    return ResponseEntity(dto, HttpStatus.OK)
  }
}
