package scheduler.services

import scheduler.models.Status
import scheduler.models.Todo
import scheduler.ports.persistence.CategoryRepository
import scheduler.ports.persistence.TodoRepository
import java.time.LocalDate

class TodoService(private val dataTodoRepository: TodoRepository,
                  private val dataCategoryRepository: CategoryRepository) {
    fun create(title: String, description: String, status: Status, due: LocalDate): Todo {
        return dataTodoRepository.create(title, description, status, due)
    }

    fun findAll(): List<Todo> {
        val todos = dataTodoRepository.findAll()
        val categoryMap = dataCategoryRepository.findAllByTodo(todos.map { it.id })

        return todos.map {
            it.copy(categories = categoryMap[it.id] ?: listOf())
        }
    }

    fun update(id: Long, title: String, description: String, status: Status, due: LocalDate): Todo {
        val todo = dataTodoRepository.update(id, title, description, status, due)
        val categories = dataCategoryRepository.findAllByTodo(todo.id)

        return todo.copy(categories = categories)
    }

    fun delete(id: Long) {
        dataTodoRepository.delete(id)
    }
}