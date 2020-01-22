package scheduler.adapters.fake

import scheduler.ports.persistence.CategoryRepository
import scheduler.ports.persistence.CategoryRepositoryTest
import scheduler.ports.persistence.TodoRepository

class FakeCategoryRepositoryTest: CategoryRepositoryTest() {

    private var categoryRelation: MutableMap<Long, MutableSet<Long>> = mutableMapOf()
    private val categoryRepository = FakeCategoryRepository(categoryRelation)

    override fun buildSubject(): CategoryRepository {
        return categoryRepository
    }

    override fun todoRepository(): TodoRepository {
        return FakeTodoRepository(categoryRepository, categoryRelation)
    }
}

