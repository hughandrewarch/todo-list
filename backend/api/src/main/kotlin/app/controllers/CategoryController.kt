package app.controllers

import app.models.category.CategoryCreateRequest
import app.models.category.CategoryResponse
import app.services.ApiCategoryService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
class CategoryController(private val service: ApiCategoryService) {

    @GetMapping("/category")
    fun categories(): List<CategoryResponse> {
        return service.findAll()
    }

    @PostMapping("/category")
    @ResponseStatus(HttpStatus.CREATED)
    fun createCategory(@Valid @RequestBody request: CategoryCreateRequest): CategoryResponse {
        return service.create(request)
    }

    @DeleteMapping("/category/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun deleteCategory(@PathVariable id: Long) {
        service.delete(id)
    }
}
