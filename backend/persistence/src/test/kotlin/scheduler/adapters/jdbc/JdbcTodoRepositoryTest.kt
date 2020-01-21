package scheduler.adapters.jdbc

import scheduler.adapters.jdbc.util.DatabaseDependencyLoader
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.context.ActiveProfiles
import scheduler.ports.persistence.TodoRepository
import scheduler.ports.persistence.TodoRepositoryTest

@ActiveProfiles(profiles = ["test"])
@SpringBootTest(classes = [DatabaseDependencyLoader::class])
class JdbcTodoRepositoryTest: TodoRepositoryTest() {

    @Autowired
    private lateinit var jdbcTemplate: JdbcTemplate

    override fun buildSubject(): TodoRepository {
        return JdbcTodoRepository(jdbcTemplate)
    }
}

