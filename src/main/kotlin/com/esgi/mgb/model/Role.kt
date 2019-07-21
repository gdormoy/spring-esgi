package com.esgi.mgb.model

import org.springframework.data.mongodb.core.mapping.Document
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Document(collection = "roles")
data class Role(
        var rolename: String,
        @Id @GeneratedValue val id: String? = null
)