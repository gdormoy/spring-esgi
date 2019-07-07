package com.esgi.mgb.model

import org.springframework.data.mongodb.core.mapping.Document
import java.util.*
import javax.persistence.*
import java.time.LocalDate
import javax.validation.constraints.Email

@Document(collection = "users")
open class User(
         var firstname: String,
         var lastname: String,
         @Email(regexp = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})")
         var email: String,
         var passWord: String,
         var userName: String,
         var birthDate: LocalDate,
         @Id
         @GeneratedValue
         val id: String = "",
         var listBar: MutableList<Bar>? = null,
         var roles: Set<Role> = HashSet(),
         var accountNonExpired: Boolean = true,
         var accountNonLocked: Boolean = true,
         var credentialsNonExpired: Boolean = true,
         var enabled: Boolean = true
)
