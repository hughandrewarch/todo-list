package app.serializers

import app.models.TodoResponse
import org.springframework.stereotype.Component
import scheduler.models.Status
import scheduler.models.Todo
import java.time.LocalDate

@Component
class TodoResponseSerializer {
    fun serialize(todo: Todo): TodoResponse {
        return TodoResponse(
            todo.id,
            todo.title,
            todo.description,
            todo.status,
            todo.due.toString()
        )
    }
}