package app.serializers

import app.models.category.CategoryResponse
import org.springframework.stereotype.Component
import scheduler.models.Todo

@Component
class TodoResponseSerializer {
    fun serialize(todo: Todo): CategoryResponse {
        return CategoryResponse(
            todo.id,
            todo.title,
            todo.description,
            todo.status,
            todo.due.toString()
        )
    }
}