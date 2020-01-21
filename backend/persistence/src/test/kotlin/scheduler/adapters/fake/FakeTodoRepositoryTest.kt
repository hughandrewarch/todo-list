package scheduler.adapters.fake

import scheduler.ports.persistence.TodoRepository
import scheduler.ports.persistence.TodoRepositoryTest

class FakeTodoRepositoryTest: TodoRepositoryTest() {
    override fun buildSubject(): TodoRepository {
        return FakeTodoRepository()
    }
}

