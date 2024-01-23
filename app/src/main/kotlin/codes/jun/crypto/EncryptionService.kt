package codes.jun.crypto

import org.springframework.security.crypto.encrypt.TextEncryptor
import org.springframework.stereotype.Service

@Service
class EncryptionService(private val textEncryptor: TextEncryptor) {
  fun encrypt(plaintext: String): String {
    return textEncryptor.encrypt(plaintext)
  }

  fun decrypt(ciphertext: String): String {
    return textEncryptor.decrypt(ciphertext)
  }
}
