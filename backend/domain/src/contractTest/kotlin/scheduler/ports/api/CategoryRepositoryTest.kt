package scheduler.ports.api

import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import scheduler.exceptions.CategoryNotFoundException
import scheduler.models.Category

abstract class CategoryRepositoryTest {
    private lateinit var subject: CategoryRepository

    @BeforeEach
    fun setUp() {
        subject = buildSubject()
    }

    abstract fun buildSubject(): CategoryRepository

    @Test
    fun create() {
        val createdCategory = subject.create("Chores")

        Assertions.assertThat(createdCategory)
            .isEqualToIgnoringGivenFields(
                Category(
                    -1,
                    "Chores"
                )
                , "id"
            )
    }

    @Test
    fun findAll() {
        val categoryA = subject.create("Category A")
        val categoryB = subject.create("Category B")
        
        val people = subject.findAll()

        Assertions.assertThat(people).containsExactlyInAnyOrder(
            Category(categoryA.id, "Category A"),
            Category(categoryB.id, "Category B")
        )
    }

    @Test
    fun `deleted category cannot be found`() {
        val category = subject.create("Chores")

        subject.delete(category.id)

        val categories = subject.findAll()

        val filtered = categories.filter { it.id == category.id }

        assertThat(filtered).isEmpty()
    }
}