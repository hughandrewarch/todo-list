package scheduler.adapters.jdbc

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.jdbc.support.KeyHolder
import scheduler.adapters.jdbc.util.preparedStatementCreator
import scheduler.exceptions.CategoryNotFoundException
import scheduler.models.Category
import scheduler.ports.persistence.CategoryRepository

class JdbcCategoryRepository(private val jdbcTemplate: JdbcTemplate) : CategoryRepository {
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