package codes.jun

import org.flywaydb.core.Flyway
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource

@Configuration
class FlywayConfiguration {
  @Autowired
  lateinit var dataSource: DataSource

  @Bean(initMethod = "migrate")
  fun flyway(): Flyway = Flyway.configure()
      .dataSource(dataSource)
      .locations("classpath:db/migration")
      .baselineOnMigrate(true)
      .load()
}