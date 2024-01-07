package codes.jun

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class JsonConfiguration {
  @Bean
  fun objectMapper(): ObjectMapper {
    return ObjectMapper()
        .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
        .registerModule(KotlinModule.Builder().build())
        .registerModule(JavaTimeModule())
  }
}