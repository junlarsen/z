package codes.jun.configuration

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.asJdbcDriver
import codes.jun.zdatabase.SecretQueries
import codes.jun.zdatabase.TodoListItemQueries
import codes.jun.zdatabase.TodoListQueries
import codes.jun.zdatabase.ZDatabase
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
  fun z(driver: SqlDriver): ZDatabase = ZDatabase(driver)

  @Bean
  fun secretQueries(z: ZDatabase): SecretQueries = z.secretQueries

  @Bean
  fun todoListQueries(z: ZDatabase): TodoListQueries = z.todoListQueries

  @Bean
  fun todoListItemQueries(z: ZDatabase): TodoListItemQueries = z.todoListItemQueries
}
