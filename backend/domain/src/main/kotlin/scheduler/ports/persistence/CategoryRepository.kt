package scheduler.ports.persistence

import scheduler.models.Category

interface CategoryRepository {
    fun create(name: String): Category
    fun find(id: Long): Category
    fun findAll(): List<Category>
    fun findAll(ids: List<Long>): List<Category>
    fun findAllByTodo(todoId: Long): List<Category>
    fun findAllByTodo(todoIds: List<Long>): Map<Long, List<Category>>
    fun delete(id: Long)
}