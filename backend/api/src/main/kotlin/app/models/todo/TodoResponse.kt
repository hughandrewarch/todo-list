package app.models.todo

import app.models.category.CategoryResponse
import scheduler.models.Status

data class TodoResponse(
        val id: Long,
        val title: String,
        val description: String,
        val status: Status,
        val due: String,
        val categories: List<CategoryResponse>
)
