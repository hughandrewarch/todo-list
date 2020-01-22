package app.configurations

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import scheduler.adapters.jdbc.JdbcCategoryRepository
import scheduler.adapters.jdbc.JdbcTodoRepository
import scheduler.ports.persistence.CategoryRepository
import scheduler.ports.persistence.TodoRepository
import scheduler.services.CategoryService
import scheduler.services.TodoService

@Configuration
class DomainServiceConfiguration {

    @Bean
    fun todoRepository(jdbcTemplate: JdbcTemplate): TodoRepository {
        return JdbcTodoRepository(jdbcTemplate)
    }

    @Bean
    fun categoryRepository(jdbcTemplate: JdbcTemplate, namedParameterJdbcTemplate: NamedParameterJdbcTemplate): CategoryRepository {
        return JdbcCategoryRepository(jdbcTemplate, namedParameterJdbcTemplate)
    }

    @Bean
    fun todoService(
        todoRepository: TodoRepository,
        categoryRepository: CategoryRepository
    ): TodoService {
        return TodoService(todoRepository, categoryRepository)
    }

    @Bean
    fun categoryService(
        categoryRepository: CategoryRepository
    ): CategoryService {
        return CategoryService(categoryRepository)
    }
}
