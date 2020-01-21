package app.models.category

import com.fasterxml.jackson.annotation.JsonProperty

data class CategoryCreateRequest(

        @JsonProperty("name")
        var name: String
)
