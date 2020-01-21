package app.models.todo

import scheduler.models.Status

data class TodoResponse(
        val id: Long,
        val title: String,
        val description: String,
        val status: Status,
        val due: String
)
