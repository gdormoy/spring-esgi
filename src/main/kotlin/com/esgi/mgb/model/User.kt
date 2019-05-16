package com.esgi.mgb.model

import org.springframework.data.mongodb.core.mapping.Document
import java.util.*
import javax.persistence.*
import javax.validation.constraints.Email

@Document(collection = "users")
data class User(
    @Id val id: String? = null,

    var firstname: String,
    var lastname: String,

    @Email(regexp = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})")
    var email: String,

    var password: String,
    var bars: List<Bar>? = mutableListOf(),
    var roles: Set<Role> = HashSet(),
    var accountNonExpired: Boolean = true,
    var accountNonLocked: Boolean = true,
    var credentialsNonExpired: Boolean = true,
    var enabled: Boolean = true
)
