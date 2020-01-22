package scheduler.models

import java.time.LocalDate

data class Todo (
    val id: Long,
    val title: String,
    val description: String,
    val status: Status,
    val due: LocalDate,
    val categories: List<Category> = emptyList()
)

enum class Status{
    PENDING,
    DONE,
}
