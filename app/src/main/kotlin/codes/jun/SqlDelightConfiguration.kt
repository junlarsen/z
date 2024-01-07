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
open class SqlDelightConfiguration {
  @Autowired
  lateinit var dataSource: DataSource

  @Bean
  open fun driver(): SqlDriver = dataSource.asJdbcDriver()

  @Bean
  open fun pivot(driver: SqlDriver): PivotDatabase = PivotDatabase(driver).also {
    PivotDatabase.Schema.create(driver)
  }

  @Bean
  open fun secretQueries(pivot: PivotDatabase): SecretQueries = pivot.secretQueries
}