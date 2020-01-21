package scheduler.exceptions.base

open class BaseNotFoundException(id: Long, name: String)
    : RuntimeException("No $name found with id <$id>")