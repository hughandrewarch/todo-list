package app.controllers

import app.models.category.CategoryCreateRequest
import app.models.category.CategoryResponse
import app.services.CategoryService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController("/category")
class CategoryController(private val service: CategoryService) {

    @GetMapping
    fun schedule(): List<CategoryResponse> {
        return service.findAll()
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createTodo(@Valid @RequestBody request: CategoryCreateRequest): CategoryResponse {
        return service.create(request)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun deleteTodo(@PathVariable id: Long) {
        service.delete(id)
    }
}
