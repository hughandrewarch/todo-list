package app.services

import app.models.category.CategoryCreateRequest
import app.models.category.CategoryResponse
import app.models.todo.TodoCreateRequest
import app.serializers.CategoryResponseSerializer
import org.springframework.stereotype.Component
import scheduler.ports.api.CategoryRepository

@Component
class CategoryService(
    private val categoryResponseSerializer: CategoryResponseSerializer,
    private val categoryRepository: CategoryRepository) {

    fun create(createRequest: CategoryCreateRequest): CategoryResponse {

        val category = categoryRepository.create(createRequest.name)

        return categoryResponseSerializer.serialize(category)
    }

    fun findAll(): List<CategoryResponse> {
        return categoryRepository.findAll().map { categoryResponseSerializer.serialize(it) }
    }

    fun delete(id: Long) {
        categoryRepository.delete(id)
    }
}
