package codes.jun.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.web.SecurityFilterChain

@Configuration
class SpringSecurityConfiguration {
  @Bean
  fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
    http {
      authorizeRequests {
        authorize(anyRequest, authenticated)
      }
      oauth2ResourceServer {
        jwt {  }
      }
      httpBasic { disable() }
      csrf { disable() }
    }
    return http.build()
  }
}
