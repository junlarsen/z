package codes.jun.user

data class UserResponseDto(
    val sub: String,
    val name: String,
    val imageUrl: String?
)