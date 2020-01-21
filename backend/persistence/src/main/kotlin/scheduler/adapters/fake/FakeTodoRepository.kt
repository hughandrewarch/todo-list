package scheduler.adapters.fake

import scheduler.exceptions.TodoNotFoundException
import scheduler.models.Status
import scheduler.models.Todo
import scheduler.ports.persistence.TodoRepository
import java.time.LocalDate

class FakeTodoRepository: TodoRepository {
    private var schedule: MutableList<Todo> = mutableListOf()

    private var id = 1L

    override fun create(title: String, description: String, status: Status, due: LocalDate): Todo {
        val todo = Todo(id++, title, description, status, due)
        schedule.add(todo)
        return todo
    }
    override fun find(id: Long): Todo {
        return schedule.firstOrNull { it.id == id } ?: throw TodoNotFoundException(id)
    }

    override fun findAll(): List<Todo> {
        return schedule
    }

    override fun update(id: Long, title: String, description: String, status: Status, due: LocalDate): Todo {
        val todo = Todo(id, title, description, status, due)

        find(id)
        delete(id)
        schedule.add(todo)

        return todo
    }

    override fun delete(id: Long) {
        schedule.removeIf { it.id == id }
    }
}

