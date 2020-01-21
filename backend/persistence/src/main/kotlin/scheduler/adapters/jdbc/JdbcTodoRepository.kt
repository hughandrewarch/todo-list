package scheduler.adapters.jdbc

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.jdbc.support.KeyHolder
import scheduler.adapters.jdbc.util.preparedStatementCreator
import scheduler.exceptions.TodoNotFoundException
import scheduler.models.Status
import scheduler.models.Todo
import scheduler.ports.persistence.TodoRepository
import java.sql.Timestamp
import java.time.LocalDate
import java.time.ZoneId
import java.util.*

class JdbcTodoRepository(private val jdbcTemplate: JdbcTemplate) : TodoRepository {
    override fun create(title: String, description: String, status: Status, due: LocalDate): Todo {
        val keyHolder: KeyHolder = GeneratedKeyHolder()

        val timestamp = Timestamp.valueOf(due.atStartOfDay())
        val cal = Calendar.getInstance(TimeZone.getDefault())

        jdbcTemplate.update(
            preparedStatementCreator(
                """
                insert into todo (title, description, status, due)
                values
                (?, ?, ?, ?)
            """
            ) { ps ->
                ps.setString(1, title)
                ps.setString(2, description)
                ps.setString(3, status.name)
                ps.setTimestamp(4, timestamp, cal)
            }, keyHolder
        )

        val id = keyHolder.key!!.toLong()

        return find(id)
    }

    override fun find(id: Long): Todo {
        return jdbcTemplate.query(
            """select id, title, description, status, due from todo where id = ?""",
            mapper,
            id
        ).singleOrNull() ?: throw TodoNotFoundException(id)
    }

    override fun findAll(): List<Todo> {
        return jdbcTemplate.query(
            """select id, title, description, status, due from todo""",
            mapper
        )
    }

    override fun update(id: Long, title: String, description: String, status: Status, due: LocalDate): Todo {
        val timestamp = Timestamp.valueOf(due.atStartOfDay())
        val cal = Calendar.getInstance(TimeZone.getDefault())

        jdbcTemplate.update(preparedStatementCreator(
            """
                update todo set
                    title = ?,
                    description = ?,
                    status = ?,
                    due = ?
                where
                    id = ?
            """
        ) { ps ->
            ps.setString(1, title)
            ps.setString(2, description)
            ps.setString(3, status.name)
            ps.setTimestamp(4, timestamp, cal)
            ps.setLong(5, id)
        })

        return find(id)
    }

    override fun delete(id: Long) {
        jdbcTemplate.update(
            """delete from todo where id = ?""",
            id
        )

    }
}

private val mapper = RowMapper { rs, _ ->
    val cal = Calendar.getInstance(TimeZone.getDefault())

    Todo(
        id = rs.getLong("id"),
        title = rs.getString("title"),
        description = rs.getString("description"),
        status = Status.valueOf(rs.getString("status")),
        due = rs.getTimestamp("due", cal).toLocalDateTime().toLocalDate()
    )
}