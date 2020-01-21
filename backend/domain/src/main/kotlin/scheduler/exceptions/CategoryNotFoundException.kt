package scheduler.exceptions

import scheduler.exceptions.base.BaseNotFoundException

class CategoryNotFoundException(id: Long): BaseNotFoundException(id, "category")
