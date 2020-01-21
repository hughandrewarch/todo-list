package app.controllers

import app.models.todo.TodoCreateRequest
import app.services.TodoService
import app.models.todo.TodoResponse
import app.models.todo.TodoUpdateRequest
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
class TodoController(private val service: TodoService) {

    @GetMapping("/schedule")
    fun schedule(): List<TodoResponse> {
        return service.findAll()
    }

    @PostMapping("/schedule/todo")
    @ResponseStatus(HttpStatus.CREATED)
    fun createTodo(@Valid @RequestBody request: TodoCreateRequest): TodoResponse {
        return service.create(request)
    }

    @PostMapping("/schedule/todo/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun updateTodo(@PathVariable id: Long, @Valid @RequestBody request: TodoUpdateRequest): TodoResponse {
        return service.update(id, request)
    }

    @DeleteMapping("/schedule/todo/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun deleteTodo(@PathVariable id: Long) {
        service.delete(id)
    }
}
