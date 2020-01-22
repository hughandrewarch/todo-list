package scheduler.ports.persistence

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import scheduler.exceptions.TodoNotFoundException
import scheduler.models.Category
import scheduler.models.Status
import scheduler.models.Todo
import java.time.LocalDate

abstract class TodoRepositoryTest {
    private lateinit var subject: TodoRepository

    @BeforeEach
    fun setUp() {
        subject = buildSubject()
    }

    abstract fun buildSubject(): TodoRepository

    @Test
    fun create() {
        val createdTodo = subject.create(
            "groceries",
            "carrots",
            Status.PENDING,
            LocalDate.of(2019, 3, 4)
        )

        assertThat(createdTodo)
            .isEqualToIgnoringGivenFields(
                Todo(
                    -1,
                    "groceries",
                    "carrots",
                    Status.PENDING,
                    LocalDate.of(2019, 3, 4)
                )
                , "id"
            )
    }

    @Test
    fun find() {
        val createdTodo = subject.create(
            "groceries",
            "carrots",
            Status.PENDING,
            LocalDate.of(2019, 3, 4)
        )

        val todo = subject.find(createdTodo.id)

        assertThat(todo).isEqualTo(
            Todo(
                createdTodo.id,
                "groceries",
                "carrots",
                Status.PENDING,
                LocalDate.of(2019, 3, 4)
            )
        )
    }

    @Test
    fun `find throws exception when no todo found`() {

        assertThatThrownBy {
            subject.find(-1L)
        }
            .isInstanceOf(TodoNotFoundException::class.java)
            .hasMessageContaining("<-1>")
    }

    @Test
    fun findAll() {
        val todoA = subject.create("a", "item a", Status.PENDING, LocalDate.of(2019, 1, 2))
        val todoB = subject.create("b", "item b", Status.PENDING, LocalDate.of(2019, 3, 4))

        val people = subject.findAll()

        assertThat(people).containsExactlyInAnyOrder(
            Todo(todoA.id, "a", "item a", Status.PENDING, LocalDate.of(2019, 1, 2)),
            Todo(todoB.id, "b", "item b", Status.PENDING, LocalDate.of(2019, 3, 4))
        )
    }

    @Test
    fun `update returns an updated todo`() {
        val createdTodo = subject.create(
            "groceries",
            "carrots",
            Status.PENDING,
            LocalDate.of(2019, 3, 4)
        )

        val todo = subject.update(
            createdTodo.id,
            "water plants",
            "ivy, succulent",
            Status.DONE,
            LocalDate.of(2019, 5, 6)
        )

        assertThat(todo).isEqualTo(
            Todo(
                createdTodo.id,
                "water plants",
                "ivy, succulent",
                Status.DONE,
                LocalDate.of(2019, 5, 6)
            )
        )
    }

    @Test
    fun `update updated todo is findable`() {
        val createdTodo = subject.create(
            "groceries",
            "carrots",
            Status.PENDING,
            LocalDate.of(2019, 3, 4)
        )

        subject.update(
            createdTodo.id,
            "water plants",
            "ivy, succulent",
            Status.DONE,
            LocalDate.of(2019, 5, 6)
        )

        val todo = subject.find(createdTodo.id)

        assertThat(todo).isEqualTo(
            Todo(
                createdTodo.id,
                "water plants",
                "ivy, succulent",
                Status.DONE,
                LocalDate.of(2019, 5, 6)
            )
        )
    }

    @Test
    fun `update throws an exception when there is no todo to be updated`() {

        assertThatThrownBy {
            subject.update(
                -1L,
                "water plants",
                "ivy, succulent",
                Status.DONE,
                LocalDate.of(2019, 5, 6)
            )
        }
            .isInstanceOf(TodoNotFoundException::class.java)
            .hasMessageContaining("<-1>")
    }

    @Test
    fun `deleted todo cannot be found`() {
        val todo = subject.create("a", "item a", Status.PENDING, LocalDate.of(2019, 1, 2))

        subject.delete(todo.id)

        assertThatThrownBy {
            subject.find(todo.id)
        }
            .isInstanceOf(TodoNotFoundException::class.java)
            .hasMessageContaining("<${todo.id}>")
    }
}