package almanac.services

import scheduler.models.Status
import scheduler.models.Todo
import scheduler.ports.persistence.TodoRepository
import java.time.LocalDate

class TodoService(private val todoRepository: TodoRepository) {
    fun create(title: String, description: String, status: Status, due: LocalDate): Todo {
        return todoRepository.create(title, description, status, due)
    }

    fun findAll(): List<Todo> {
        return todoRepository.findAll()
    }

    fun update(id: Long, title: String, description: String, status: Status, due: LocalDate): Todo {
        return todoRepository.update(id, title, description, status, due)
    }

    fun delete(id: Long) {
        todoRepository.delete(id)
    }
}