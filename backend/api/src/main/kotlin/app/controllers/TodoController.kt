package app.controllers

import app.models.category.CategoryCreateRequest
import app.services.ApiTodoService
import app.models.category.CategoryResponse
import app.models.todo.TodoUpdateRequest
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
class TodoController(private val service: ApiTodoService) {

    @GetMapping("/schedule")
    fun schedule(): List<CategoryResponse> {
        return service.findAll()
    }

    @PostMapping("/schedule/todo")
    @ResponseStatus(HttpStatus.CREATED)
    fun createTodo(@Valid @RequestBody request: CategoryCreateRequest): CategoryResponse {
        return service.create(request)
    }

    @PostMapping("/schedule/todo/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun updateTodo(@PathVariable id: Long, @Valid @RequestBody request: TodoUpdateRequest): CategoryResponse {
        return service.update(id, request)
    }

    @DeleteMapping("/schedule/todo/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun deleteTodo(@PathVariable id: Long) {
        service.delete(id)
    }
}
