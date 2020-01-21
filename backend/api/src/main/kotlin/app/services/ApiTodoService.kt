package app.services

import app.models.TodoCreateRequest
import scheduler.ports.api.TodoRepository
import app.models.TodoResponse
import app.models.TodoUpdateRequest
import app.serializers.TodoResponseSerializer
import org.springframework.stereotype.Component
import scheduler.models.Status
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Component
class ApiTodoService(
    private val todoResponseSerializer: TodoResponseSerializer,
    private val todoRepository: TodoRepository) {

    fun create(createRequest: TodoCreateRequest): TodoResponse {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

        val todo = todoRepository.create(
            createRequest.title,
            createRequest.description,
            Status.valueOf(createRequest.status),
            LocalDate.parse(createRequest.due, formatter)
        )
        return todoResponseSerializer.serialize(todo)
    }

    fun findAll(): List<TodoResponse> {
        return todoRepository.findAll().map { todoResponseSerializer.serialize(it) }
    }

    fun update(id: Long, updateRequest: TodoUpdateRequest): TodoResponse {
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
