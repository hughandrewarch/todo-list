package scheduler.adapters.fake

import scheduler.exceptions.CategoryNotFoundException
import scheduler.models.Category
import scheduler.ports.persistence.CategoryRepository

class FakeCategoryRepository(private val categoryRelation: MutableMap<Long, MutableSet<Long>>) : CategoryRepository {

    private var categories: MutableList<Category> = mutableListOf()

    private var id = 1L

    override fun create(name: String): Category {
        val category = Category(id++, name)
        categories.add(category)
        return category
    }

    override fun find(id: Long): Category {
        return categories.firstOrNull { it.id == id } ?: throw CategoryNotFoundException(id)
    }

    override fun findAll(): List<Category> {
        return categories
    }

    override fun findAll(ids: List<Long>): List<Category> {
        return categories.filter {
            ids.contains(it.id)
        }
    }

    override fun findAllByTodo(todoId: Long): List<Category> {
        val categoryIds = categoryRelation[todoId] ?: mutableSetOf()

        return findAll(categoryIds.toList())
    }

    override fun findAllByTodo(todoIds: List<Long>): Map<Long, List<Category>> {
        val categoryMap: MutableMap<Long, List<Category>> = mutableMapOf()

        todoIds.forEach { todoId ->
            val categoryIds = categoryRelation[todoId] ?: mutableSetOf()
            categoryMap[todoId] = findAll(categoryIds.toList())
        }

        return categoryMap
    }

    override fun delete(id: Long) {
        categories.removeIf { it.id == id }
    }
}

