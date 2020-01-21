package scheduler.adapters.fake

import scheduler.ports.persistence.CategoryRepository
import scheduler.ports.persistence.CategoryRepositoryTest

class FakeCategoryRepositoryTest: CategoryRepositoryTest() {
    override fun buildSubject(): CategoryRepository {
        return FakeCategoryRepository()
    }
}

