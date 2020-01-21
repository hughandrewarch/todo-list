package app.adapters

import scheduler.services.CategoryService
import org.springframework.stereotype.Component
import scheduler.models.Category
import scheduler.ports.api.CategoryRepository

@Component
class DomainCategoryRepository(private val service: CategoryService): CategoryRepository {
    override fun create(name: String): Category {
        return service.create(name)
    }

    override fun findAll(): List<Category> {
        return service.findAll()
    }

    override fun delete(id: Long) {
        return service.delete(id)
    }
}
