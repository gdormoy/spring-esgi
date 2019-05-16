package com.esgi.mgb.model

abstract class AbstractUser {
    abstract val id: String?
    abstract var firstname: String
    abstract var lastname: String
    abstract var email: String
    abstract var password: String
    abstract var bars: List<Bar>?
    abstract var roles: Set<Role>
    abstract var accountNonExpired: Boolean
    abstract var accountNonLocked: Boolean
    abstract var credentialsNonExpired: Boolean
    abstract var enabled: Boolean
}