package scheduler.adapters.jdbc

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.jdbc.support.KeyHolder
import scheduler.adapters.jdbc.util.preparedStatementCreator
import scheduler.exceptions.CategoryNotFoundException
import scheduler.models.Category
import scheduler.ports.persistence.CategoryRepository
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import scheduler.models.CategoryRelation


class JdbcCategoryRepository(
    private val jdbcTemplate: JdbcTemplate,
    private val namedParameterJdbcTemplate: NamedParameterJdbcTemplate
) : CategoryRepository {
    override fun create(name: String): Category {
        val keyHolder: KeyHolder = GeneratedKeyHolder()

        jdbcTemplate.update(
            preparedStatementCreator(
                """
                insert into category (name)
                values
                (?)
            """
            ) { ps ->
                ps.setString(1, name)
            }, keyHolder
        )

        val id = keyHolder.key!!.toLong()

        return find(id)
    }

    override fun find(id: Long): Category {
        return jdbcTemplate.query(
            """select id, name from category where id = ?""",
            mapper,
            id
        ).singleOrNull() ?: throw CategoryNotFoundException(id)
    }

    override fun findAll(): List<Category> {
        return jdbcTemplate.query(
            """select id, name from category""",
            mapper
        )
    }

    override fun findAll(ids: List<Long>): List<Category> {

        val parameters = MapSqlParameterSource()
        parameters.addValue("ids", ids)

        return namedParameterJdbcTemplate.query(
            """select id, name from category where id in (:ids)""",
            parameters,
            mapper
        )
    }

    override fun findAllByTodo(todoId: Long): List<Category> {
        return jdbcTemplate.query(
            """ select c.id, c.name
                from category c, catergory_relation cr
                where cr.todo_id = ? and cr.cat_id = c.id
            """.trimMargin(),
            mapper,
            todoId
        )
    }

    override fun findAllByTodo(todoIds: List<Long>): Map<Long, List<Category>> {
        val parameters = MapSqlParameterSource()
        parameters.addValue("todoIds", todoIds)

        val categoryRelations = namedParameterJdbcTemplate.query(
            """
                SELECT cr.todo_id, c.id, c.name
                FROM category_relation cr
                JOIN category c ON c.id = cr.cat_id
                WHERE cr.todo_id in (:todoIds)
            """.trimIndent(),
            parameters,
            relationMapper
        )

         return categoryRelations.groupBy {
            it.todoId
        }.mapValues { entry ->
            entry.value.map { it.category }
        }
    }

    override fun delete(id: Long) {
        jdbcTemplate.update(
            """delete from category where id = ?""",
            id
        )
    }
}

private val mapper = RowMapper { rs, _ ->
    Category(
        id = rs.getLong("id"),
        name = rs.getString("name")
    )
}

private val relationMapper = RowMapper { rs, _ ->
    CategoryRelation(
        todoId = rs.getLong("cr.todo_id"),
        category = Category(
            id = rs.getLong("c.id"),
            name = rs.getString("c.name")
        )
    )
}