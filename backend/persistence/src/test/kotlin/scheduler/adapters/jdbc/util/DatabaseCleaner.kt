package scheduler.adapters.jdbc.util

import org.slf4j.LoggerFactory
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.SQLException

class DatabaseCleaner(private val connection: Connection) {
    private val tablesToExclude = mutableSetOf<String>()

    fun excludeTables(vararg tableNames: String) {
        tablesToExclude += tableNames.flatMap { listOf(it, it.toLowerCase()) }
    }

    fun clean() {

        val tableRefs = getTableNames()

        executeReset(tableRefs)

        closeConnection()

    }

    private fun getTableNames(): List<String> {
        val connection = connection
        val tables = connection.metaData.getTables(connection.catalog, null, null, arrayOf("TABLE"))

        return tables.let {
            generateSequence {
                if (it.next()) {
                    val tableName = it.getString("TABLE_NAME")
                    tableName
                } else {
                    null
                }
            }
                    .filterNot(tablesToExclude::contains)
                    .toList()
        }
    }

    private fun executeReset(tables: List<String>) {
        try {
            val reset = buildClearStatement(tables)
            reset.executeBatch()
        } catch (e: SQLException) {
            LOG.error("Failed to remove rows because {}. InnoDb status: {}", e)
            throw e
        }
    }

    private fun buildClearStatement(tables: List<String>): PreparedStatement {
        val reset = connection.prepareStatement("")
        reset.addBatch("SET FOREIGN_KEY_CHECKS = 0")
        tables.forEach { table ->
            reset.addBatch("DELETE FROM `$table`")
        }
        reset.addBatch("SET FOREIGN_KEY_CHECKS = 1")
        return reset
    }

    private fun closeConnection() {
        connection.close()
    }

    companion object {
        private val LOG = LoggerFactory.getLogger(DatabaseCleaner::class.java)!!
    }
}
