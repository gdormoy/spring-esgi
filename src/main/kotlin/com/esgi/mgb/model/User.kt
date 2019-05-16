package com.esgi.mgb.model

import org.springframework.data.mongodb.core.mapping.Document
import java.util.*
import javax.persistence.*
import javax.validation.constraints.Email

@Document(collection = "users")
data class User(
    @Id override val id: String? = null,

    override var firstname: String,
    override var lastname: String,
    @Email(regexp = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})")
    override var email: String,
    override var password: String,
    override var bars: List<Bar>? = mutableListOf(),
    override var roles: Set<Role> = HashSet(),
    override var accountNonExpired: Boolean = true,
    override var accountNonLocked: Boolean = true,
    override var credentialsNonExpired: Boolean = true,
    override var enabled: Boolean = true
): AbstractUser()
