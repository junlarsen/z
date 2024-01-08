package codes.jun

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.asJdbcDriver
import codes.jun.pivot.PivotDatabase
import codes.jun.pivot.SecretQueries
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource

@Configuration
class SqlDelightConfiguration {
  @Autowired
  lateinit var dataSource: DataSource

  @Bean
  fun driver(): SqlDriver = dataSource.asJdbcDriver()

  @Bean
  fun pivot(driver: SqlDriver): PivotDatabase = PivotDatabase(driver)

  @Bean
  fun secretQueries(pivot: PivotDatabase): SecretQueries = pivot.secretQueries
}
