package app.configurations

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.JdbcTemplate
import scheduler.adapters.jdbc.JdbcCategoryRepository
import scheduler.adapters.jdbc.JdbcTodoRepository
import scheduler.services.CategoryService
import scheduler.services.TodoService

@Configuration
class DomainServiceConfiguration {

    @Bean
    fun todoService(jdbcTemplate: JdbcTemplate): TodoService {
        val todoRepo = JdbcTodoRepository(jdbcTemplate)

        return TodoService(todoRepo)
    }

    @Bean
    fun categoryService(jdbcTemplate: JdbcTemplate): CategoryService {
        val categoryRepo = JdbcCategoryRepository(jdbcTemplate)

        return CategoryService(categoryRepo)
    }
}
