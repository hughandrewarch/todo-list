package app.serializers

import app.models.todo.TodoResponse
import org.springframework.stereotype.Component
import scheduler.models.Todo

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