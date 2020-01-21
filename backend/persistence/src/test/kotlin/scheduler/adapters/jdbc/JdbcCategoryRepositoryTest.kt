package scheduler.adapters.jdbc

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.context.ActiveProfiles
import scheduler.adapters.jdbc.util.DatabaseDependencyLoader
import scheduler.ports.persistence.CategoryRepository
import scheduler.ports.persistence.CategoryRepositoryTest

@ActiveProfiles(profiles = ["test"])
@SpringBootTest(classes = [DatabaseDependencyLoader::class])
class JdbcCategoryRepositoryTest: CategoryRepositoryTest() {

    @Autowired
    private lateinit var jdbcTemplate: JdbcTemplate

    override fun buildSubject(): CategoryRepository {
        return JdbcCategoryRepository(jdbcTemplate)
    }
}
