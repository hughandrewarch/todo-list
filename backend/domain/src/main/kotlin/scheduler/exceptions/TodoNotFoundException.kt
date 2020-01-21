package scheduler.exceptions

import scheduler.exceptions.base.BaseNotFoundException

class TodoNotFoundException(id: Long): BaseNotFoundException(id, "todo")
