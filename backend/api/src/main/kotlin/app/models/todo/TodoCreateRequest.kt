package app.models.todo

import com.fasterxml.jackson.annotation.JsonProperty

data class TodoCreateRequest(

        @JsonProperty("title")
        var title: String,

        @JsonProperty("description")
        var description: String,

        @JsonProperty("status")
        var status: String,

        @JsonProperty("due")
        var due: String
)
