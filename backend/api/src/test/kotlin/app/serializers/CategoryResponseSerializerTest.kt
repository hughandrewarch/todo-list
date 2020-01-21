package app.serializers

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import scheduler.models.Category

internal class CategoryResponseSerializerTest {

    private val subject = CategoryResponseSerializer()

    @Test
    fun serialize() {
        val category = Category(1L, "Chores")

        val serialized = subject.serialize(category)

        assertThat(serialized.id).isEqualTo(1L)
        assertThat(serialized.name).isEqualTo("Chores")
    }
}