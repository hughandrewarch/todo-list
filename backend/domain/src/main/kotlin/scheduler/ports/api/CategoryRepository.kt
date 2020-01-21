package scheduler.ports.api

import scheduler.models.Category

interface CategoryRepository {
    fun create(name: String): Category
    fun findAll(): List<Category>
    fun delete(id: Long)
}