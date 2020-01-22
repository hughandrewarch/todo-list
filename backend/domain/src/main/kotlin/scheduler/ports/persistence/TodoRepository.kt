package scheduler.ports.persistence

import scheduler.models.Status
import scheduler.models.Todo
import java.time.LocalDate

interface TodoRepository {
    fun create(title: String, description: String, status: Status, due: LocalDate): Todo
    fun find(id: Long): Todo
    fun findAll(): List<Todo>
    fun delete(id: Long)
    fun update(id: Long, title: String, description: String, status: Status, due: LocalDate): Todo
    fun addCategory(id: Long, catId: Long)
    fun removeCategory(id: Long, catId: Long)
}