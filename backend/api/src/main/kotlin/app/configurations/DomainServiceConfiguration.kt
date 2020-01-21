package app.configurations

import almanac.services.TodoService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.JdbcTemplate
import scheduler.adapters.fake.FakeTodoRepository
import scheduler.adapters.jdbc.JdbcTodoRepository
import scheduler.models.Status
import java.time.LocalDate

@Configuration
class DomainServiceConfiguration {

    @Bean
    fun todoService(jdbcTemplate: JdbcTemplate): TodoService {
        val todoRepo = JdbcTodoRepository(jdbcTemplate)
        val fakeRepo = FakeTodoRepository()

        fakeRepo.create("a", "item a", Status.PENDING, LocalDate.of(2019, 1, 2))
        fakeRepo.create("b", "item b", Status.PENDING, LocalDate.of(2019, 3, 4))

        return TodoService(todoRepo)
    }
}
