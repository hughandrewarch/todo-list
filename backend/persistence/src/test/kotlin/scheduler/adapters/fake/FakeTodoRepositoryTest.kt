package scheduler.adapters.fake

import scheduler.ports.persistence.TodoRepository
import scheduler.ports.persistence.TodoRepositoryTest

class FakeTodoRepositoryTest: TodoRepositoryTest() {

    private var categoryRelation: MutableMap<Long, MutableSet<Long>> = mutableMapOf()
    private val categoryRepository = FakeCategoryRepository(categoryRelation)

    override fun buildSubject(): TodoRepository {
        return FakeTodoRepository(categoryRepository, categoryRelation)
    }
}

