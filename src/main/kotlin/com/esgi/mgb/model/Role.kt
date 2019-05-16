package com.esgi.mgb.model

import org.springframework.data.mongodb.core.mapping.Document
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Document(collection = "roles")
data class Role(
    override var rolename: String,
    @Id override val id: String? = null
) : AbstractRole()