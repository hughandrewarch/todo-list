package scheduler.adapters.fake

import scheduler.exceptions.TodoNotFoundException
import scheduler.models.Category
import scheduler.models.Status
import scheduler.models.Todo
import scheduler.ports.persistence.TodoRepository
import java.time.LocalDate

class FakeTodoRepository(
    private val categoryRepository: FakeCategoryRepository,
    private val categoryRelation: MutableMap<Long, MutableSet<Long>>
) : TodoRepository {

    private val schedule: MutableList<Todo> = mutableListOf()

    private var id = 1L

    override fun create(title: String, description: String, status: Status, due: LocalDate): Todo {
        val todo = Todo(id++, title, description, status, due)
        schedule.add(todo)
        return todo
    }
    override fun find(id: Long): Todo {
        val todo = schedule.firstOrNull { it.id == id } ?: throw TodoNotFoundException(id)

        val categories: MutableList<Category> = mutableListOf()
        categoryRelation[todo.id]?.forEach {
            categories.add(categoryRepository.find(it))
        }

        return todo.copy(categories = categories)
    }

    override fun findAll(): List<Todo> {
        return schedule.map {
            find(it.id)
        }
    }

    override fun update(id: Long, title: String, description: String, status: Status, due: LocalDate): Todo {
        val todo = Todo(id, title, description, status, due)
        find(id)

        replace(todo)

        return todo
    }

    override fun delete(id: Long) {
        schedule.removeIf { it.id == id }
    }

    override fun addCategory(id: Long, catId: Long) {
        val relationSet = categoryRelation[id] ?: mutableSetOf()
        relationSet.add(catId)
        categoryRelation[id] = relationSet
    }

    override fun removeCategory(id: Long, catId: Long) {
        categoryRelation[id]?.remove(catId)
    }

    private fun replace(copy: Todo) {
        delete(copy.id)
        schedule.add(copy)
    }
}

