package app.adapters

import almanac.services.TodoService
import org.springframework.stereotype.Component
import scheduler.models.Status
import scheduler.models.Todo
import scheduler.ports.api.TodoRepository
import java.time.LocalDate

@Component
class DomainTodoRepository(private val service: TodoService): TodoRepository {
    override fun create(title: String, description: String, status: Status, due: LocalDate): Todo {
        return service.create(title, description, status, due)
    }

    override fun findAll(): List<Todo> {
        return service.findAll()
    }

    override fun update(id: Long, title: String, description: String, status: Status, due: LocalDate): Todo {
        return service.update(id, title, description, status, due)
    }

    override fun delete(id: Long) {
        service.delete(id)
    }
}
