package scheduler.ports.persistence

import scheduler.models.Category

interface CategoryRepository {
    fun create(name: String): Category
    fun find(id: Long): Category
    fun findAll(): List<Category>
    fun delete(id: Long)
}