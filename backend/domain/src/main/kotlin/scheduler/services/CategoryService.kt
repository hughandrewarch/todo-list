package scheduler.services

import scheduler.models.Category
import scheduler.ports.persistence.CategoryRepository

class CategoryService(private val dataCategoryRepository: CategoryRepository) {
    fun create(name: String): Category {
        return dataCategoryRepository.create(name)
    }

    fun findAll(): List<Category> {
        return dataCategoryRepository.findAll()
    }

    fun delete(id: Long) {
        dataCategoryRepository.delete(id)
    }
}