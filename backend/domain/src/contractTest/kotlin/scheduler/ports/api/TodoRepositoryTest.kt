package scheduler.ports.api

import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import scheduler.exceptions.TodoNotFoundException
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

        Assertions.assertThat(createdTodo)
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
    fun findAll() {
        val todoA = subject.create("a", "item a", Status.PENDING, LocalDate.of(2019, 1, 2))
        val todoB = subject.create("b", "item b", Status.PENDING, LocalDate.of(2019, 3, 4))

        val people = subject.findAll()

        Assertions.assertThat(people).containsExactlyInAnyOrder(
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

        Assertions.assertThat(todo).isEqualTo(
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

        val todo = subject.findAll().filter { it.id == createdTodo.id }

        Assertions.assertThat(todo).isEqualTo(
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

        Assertions.assertThatThrownBy {
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
        val createdTodo = subject.create("a", "item a", Status.PENDING, LocalDate.of(2019, 1, 2))

        subject.delete(createdTodo.id)

        val todo = subject.findAll().filter { it.id == createdTodo.id }

        assertThat(todo).isEmpty()
    }
}