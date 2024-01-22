package codes.jun

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.encrypt.Encryptors
import org.springframework.security.crypto.encrypt.TextEncryptor
import org.springframework.security.crypto.keygen.KeyGenerators

@Configuration
class EncryptorConfiguration {
  @Value("\${z.encryption-secret}")
  private lateinit var encryptionSecret: String

  @Bean
  fun textEncryptor(): TextEncryptor {
    val salt = KeyGenerators.string().generateKey()
    return Encryptors.text(encryptionSecret, salt)
  }
}