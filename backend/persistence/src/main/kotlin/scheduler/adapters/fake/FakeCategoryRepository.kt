package scheduler.adapters.fake

import scheduler.exceptions.CategoryNotFoundException
import scheduler.models.Category
import scheduler.ports.persistence.CategoryRepository

class FakeCategoryRepository: CategoryRepository {

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

    override fun delete(id: Long) {
        categories.removeIf { it.id == id }
    }
}

