package scheduler.ports.persistence

import org.assertj.core.api.Assertions
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
    fun find() {
        val createdCategory = subject.create("Chores")

        val category = subject.find(createdCategory.id)

        Assertions.assertThat(category).isEqualTo(
            Category(
                createdCategory.id,
                "Chores"
            )
        )
    }

    @Test
    fun `find throws exception when no category found`() {

        Assertions.assertThatThrownBy {
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

        Assertions.assertThat(people).containsExactlyInAnyOrder(
            Category(categoryA.id, "Category A"),
            Category(categoryB.id, "Category B")
        )
    }

    @Test
    fun `deleted category cannot be found`() {
        val category = subject.create("Chores")

        subject.delete(category.id)

        Assertions.assertThatThrownBy {
            subject.find(category.id)
        }
            .isInstanceOf(CategoryNotFoundException::class.java)
            .hasMessageContaining("<${category.id}>")
    }
}