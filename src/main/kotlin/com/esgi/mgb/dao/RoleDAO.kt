package com.esgi.mgb.dao

import com.esgi.mgb.model.Role
import org.springframework.data.mongodb.repository.MongoRepository

interface RoleDAO : MongoRepository<Role, Long> {
    fun findByRolename(rolename: String): Role
}