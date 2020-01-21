package app.services

import app.models.category.CategoryCreateRequest
import scheduler.ports.api.TodoRepository
import app.models.category.CategoryResponse
import app.models.todo.TodoUpdateRequest
import app.serializers.TodoResponseSerializer
import org.springframework.stereotype.Component
import scheduler.models.Status
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Component
class ApiTodoService(
    private val todoResponseSerializer: TodoResponseSerializer,
    private val todoRepository: TodoRepository) {

    fun create(createRequest: CategoryCreateRequest): CategoryResponse {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

        val todo = todoRepository.create(
            createRequest.title,
            createRequest.description,
            Status.valueOf(createRequest.status),
            LocalDate.parse(createRequest.due, formatter)
        )
        return todoResponseSerializer.serialize(todo)
    }

    fun findAll(): List<CategoryResponse> {
        return todoRepository.findAll().map { todoResponseSerializer.serialize(it) }
    }

    fun update(id: Long, updateRequest: TodoUpdateRequest): CategoryResponse {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

        val todo = todoRepository.update(
            id,
            updateRequest.title,
            updateRequest.description,
            Status.valueOf(updateRequest.status),
            LocalDate.parse(updateRequest.due, formatter)
        )
        return todoResponseSerializer.serialize(todo)

    }

    fun delete(id: Long) {
        todoRepository.delete(id)
    }
}
