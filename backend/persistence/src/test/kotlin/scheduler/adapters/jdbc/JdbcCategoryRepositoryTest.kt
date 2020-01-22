package scheduler.adapters.jdbc

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.test.context.ActiveProfiles
import scheduler.adapters.jdbc.util.DatabaseDependencyLoader
import scheduler.ports.persistence.CategoryRepository
import scheduler.ports.persistence.CategoryRepositoryTest
import scheduler.ports.persistence.TodoRepository

@ActiveProfiles(profiles = ["test"])
@SpringBootTest(classes = [DatabaseDependencyLoader::class])
class JdbcCategoryRepositoryTest: CategoryRepositoryTest() {
    @Autowired
    private lateinit var jdbcTemplate: JdbcTemplate
    @Autowired
    private lateinit var namedParameterJdbcTemplate: NamedParameterJdbcTemplate

    override fun buildSubject(): CategoryRepository {
        return JdbcCategoryRepository(jdbcTemplate, namedParameterJdbcTemplate)
    }

    override fun todoRepository(): TodoRepository {
        return JdbcTodoRepository(jdbcTemplate)
    }
}
