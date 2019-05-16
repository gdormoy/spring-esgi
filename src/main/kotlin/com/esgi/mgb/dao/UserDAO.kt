package com.esgi.mgb.dao

import com.esgi.mgb.model.Bar
import com.esgi.mgb.model.User
import org.springframework.data.mongodb.repository.MongoRepository

interface UserDAO : MongoRepository<User, String> {
    fun findByBar(bar: Bar): User?
    fun findByPseudoRegex(name: String): List<User>
}