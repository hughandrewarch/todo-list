package scheduler.adapters.jdbc.util

import org.springframework.jdbc.core.PreparedStatementCreator
import java.sql.PreparedStatement
import java.sql.Statement

fun preparedStatementCreator(sql: String, setter: (PreparedStatement) -> Unit) =
        PreparedStatementCreator { connection ->
            val ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
            setter(ps)
            ps
        }