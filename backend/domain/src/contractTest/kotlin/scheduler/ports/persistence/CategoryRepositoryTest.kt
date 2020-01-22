package scheduler.ports.persistence

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import scheduler.exceptions.CategoryNotFoundException
import scheduler.models.Category
import scheduler.models.Status
import scheduler.models.Todo
import java.time.LocalDate

abstract class CategoryRepositoryTest {
    private lateinit var subject: CategoryRepository
    private lateinit var todoRepository: TodoRepository

    @BeforeEach
    fun setUp() {
        subject = buildSubject()
        todoRepository = todoRepository()
    }

    abstract fun buildSubject(): CategoryRepository
    abstract fun todoRepository(): TodoRepository

    @Test
    fun create() {
        val createdCategory = subject.create("Chores")

        assertThat(createdCategory)
            .isEqualToIgnoringGivenFields(
                Category(
                    -1,
                    "Chores"
                )
                , "id"
            )
    }

    @Test
    fun find() {
        val createdCategory = subject.create("Chores")

        val category = subject.find(createdCategory.id)

        assertThat(category).isEqualTo(
            Category(
                createdCategory.id,
                "Chores"
            )
        )
    }

    @Test
    fun `find throws exception when no category found`() {

        assertThatThrownBy {
            subject.find(-1L)
        }
            .isInstanceOf(CategoryNotFoundException::class.java)
            .hasMessageContaining("<-1>")
    }

    @Test
    fun findAll() {
        val categoryA = subject.create("Category A")
        val categoryB = subject.create("Category B")

        val people = subject.findAll()

        assertThat(people).containsExactlyInAnyOrder(
            Category(categoryA.id, "Category A"),
            Category(categoryB.id, "Category B")
        )
    }

    @Test
    fun `findAll return filtered items`() {
        val categoryA = subject.create("Category A")
        val categoryB = subject.create("Category B")
        val categoryC = subject.create("Category C")

        val people = subject.findAll(listOf(categoryA.id, categoryC.id))

        assertThat(people).containsExactlyInAnyOrder(
            Category(categoryA.id, "Category A"),
            Category(categoryC.id, "Category C")
        )
    }

    @Test
    fun `findAllByTodo return filtered items`() {
        val categoryA = subject.create("Category A")
        val categoryB = subject.create("Category B")
        val categoryC = subject.create("Category C")

        val todo = todoRepository.create("a", "b", Status.DONE, LocalDate.of(2020, 1, 1))
        todoRepository.addCategory(todo.id, categoryA.id)
        todoRepository.addCategory(todo.id, categoryB.id)

        val people = subject.findAllByTodo(todo.id)

        assertThat(people).containsExactlyInAnyOrder(
            Category(categoryA.id, "Category A"),
            Category(categoryB.id, "Category B")
        )
    }

    @Test
    fun `findAllByTodo with multiple todos`() {
        val categoryA = subject.create("Category A")
        val categoryB = subject.create("Category B")
        val categoryC = subject.create("Category C")

        val todoA = todoRepository.create("a", "1", Status.DONE, LocalDate.of(2020, 1, 1))
        val todoB = todoRepository.create("b", "2", Status.DONE, LocalDate.of(2020, 1, 1))

        todoRepository.addCategory(todoA.id, categoryA.id)
        todoRepository.addCategory(todoB.id, categoryB.id)
        todoRepository.addCategory(todoB.id, categoryC.id)

        val categoryMap = subject.findAllByTodo(listOf(todoA.id, todoB.id))

        assertThat(categoryMap).containsEntry(
            todoA.id,
            listOf(Category(categoryA.id, "Category A"))
        )

        assertThat(categoryMap).containsEntry(
            todoB.id,
            listOf(Category(categoryB.id, "Category B"),Category(categoryC.id, "Category C"))
        )
    }

    @Test
    fun `deleted category cannot be found`() {
        val category = subject.create("Chores")

        subject.delete(category.id)

        assertThatThrownBy {
            subject.find(category.id)
        }
            .isInstanceOf(CategoryNotFoundException::class.java)
            .hasMessageContaining("<${category.id}>")
    }

    @Test
    fun `addCategory added categories are returned`() {
        val createdTodo = todoRepository.create(
            "groceries",
            "carrots",
            Status.PENDING,
            LocalDate.of(2019, 3, 4)
        )

        val categoryA = subject.create("Chores")
        val categoryB = subject.create("Weekend")

        todoRepository.addCategory(createdTodo.id, categoryA.id)
        todoRepository.addCategory(createdTodo.id, categoryB.id)

        val todo = subject.findAllByTodo(createdTodo.id)

        assertThat(todo).containsExactlyInAnyOrder(
            Category(categoryA.id, "Chores"),
            Category(categoryB.id, "Weekend")
        )
    }

    @Test
    fun `removeCategory removed categories are not returned`() {
        val createdTodo = todoRepository.create(
            "groceries",
            "carrots",
            Status.PENDING,
            LocalDate.of(2019, 3, 4)
        )

        val categoryA = subject.create("Chores")
        val categoryB = subject.create("Weekend")

        todoRepository.addCategory(createdTodo.id, categoryA.id)
        todoRepository.addCategory(createdTodo.id, categoryB.id)

        todoRepository.removeCategory(createdTodo.id, categoryA.id)

        val todo = subject.findAllByTodo(createdTodo.id)

        assertThat(todo).containsExactlyInAnyOrder(
            Category(categoryB.id, "Weekend")
        )
    }
}