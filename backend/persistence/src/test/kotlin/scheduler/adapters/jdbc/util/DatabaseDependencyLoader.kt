package scheduler.adapters.jdbc.util

import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles(profiles = ["test"])
@SpringBootApplication
@ExtendWith(DatabaseCleanerExtension::class)
open class DatabaseDependencyLoader {
    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(DatabaseDependencyLoader::class.java, *args)
        }
    }
}
