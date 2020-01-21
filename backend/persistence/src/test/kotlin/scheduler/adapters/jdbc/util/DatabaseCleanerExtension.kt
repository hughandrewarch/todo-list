package scheduler.adapters.jdbc.util

import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.springframework.stereotype.Component
import org.springframework.test.context.junit.jupiter.SpringExtension
import javax.sql.DataSource

@Component
class DatabaseCleanerExtension : BeforeEachCallback {

    override fun beforeEach(context: ExtensionContext) {
        val applicationContext = SpringExtension.getApplicationContext(context)
        val dataSource = applicationContext.getBean("dataSource", DataSource::class) as DataSource

        val cleaner = DatabaseCleaner(dataSource.connection)
        cleaner.excludeTables("flyway_schema_history")
        cleaner.clean()
    }
}
