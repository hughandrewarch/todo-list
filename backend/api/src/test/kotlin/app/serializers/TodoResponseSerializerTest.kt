package app.serializers

import app.models.category.CategoryResponse
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import scheduler.models.Category
import scheduler.models.Status
import scheduler.models.Todo
import java.time.LocalDate

internal class TodoResponseSerializerTest {

    private val categoryResponseSerializer = CategoryResponseSerializer()
    private val subject = TodoResponseSerializer(categoryResponseSerializer)

    @Test
    fun serialize() {
        val todo = Todo(
            1L,
            "Groceries",
            "Buy carrots",
            Status.PENDING,
            LocalDate.of(2020, 2, 3),
            listOf(Category(1, "A"), Category(2, "B"))
        )

        val serialized = subject.serialize(todo)

        assertThat(serialized.id).isEqualTo(1L)
        assertThat(serialized.title).isEqualTo("Groceries")
        assertThat(serialized.description).isEqualTo("Buy carrots")
        assertThat(serialized.status).isEqualTo(Status.PENDING)
        assertThat(serialized.due).isEqualTo("2020-02-03")
        assertThat(serialized.categories).containsExactlyInAnyOrder(
            CategoryResponse(1, "A"),
            CategoryResponse(2, "B")
        )
    }
}