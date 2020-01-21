package scheduler.services

import scheduler.models.Status
import scheduler.models.Todo
import scheduler.ports.persistence.TodoRepository
import java.time.LocalDate

class TodoService(private val dataTodoRepository: TodoRepository) {
    fun create(title: String, description: String, status: Status, due: LocalDate): Todo {
        return dataTodoRepository.create(title, description, status, due)
    }

    fun findAll(): List<Todo> {
        return dataTodoRepository.findAll()
    }

    fun update(id: Long, title: String, description: String, status: Status, due: LocalDate): Todo {
        return dataTodoRepository.update(id, title, description, status, due)
    }

    fun delete(id: Long) {
        dataTodoRepository.delete(id)
    }
}