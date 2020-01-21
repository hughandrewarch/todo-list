package app.serializers

import app.models.category.CategoryResponse
import org.springframework.stereotype.Component
import scheduler.models.Category

@Component
class CategoryResponseSerializer {
    fun serialize(category: Category): CategoryResponse {
        return CategoryResponse(
            category.id,
            category.name
        )
    }
}