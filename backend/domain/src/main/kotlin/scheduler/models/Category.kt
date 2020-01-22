package scheduler.models

data class Category(
    val id: Long,
    val name: String
)

data class CategoryRelation(
    val todoId: Long,
    val category: Category
)

