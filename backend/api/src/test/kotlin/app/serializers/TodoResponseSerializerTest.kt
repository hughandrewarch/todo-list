package app.serializers

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import scheduler.models.Status
import scheduler.models.Todo
import java.time.LocalDate

internal class TodoResponseSerializerTest {

    private val subject = TodoResponseSerializer()

    @Test
    fun serialize() {
        val todo = Todo(1L, "Groceries", "Buy carrots", Status.PENDING, LocalDate.of(2020, 2, 3))

        val serialized = subject.serialize(todo)

        assertThat(todo.id).isEqualTo(1L)
        assertThat(todo.title).isEqualTo("Groceries")
        assertThat(todo.description).isEqualTo("Buy carrots")
        assertThat(todo.status).isEqualTo(Status.PENDING)
        assertThat(todo.due).isEqualTo("2020-02-03")
    }
}