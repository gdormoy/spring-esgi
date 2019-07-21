package com.esgi.mgb.dao

import com.esgi.mgb.model.User
import org.springframework.data.mongodb.repository.MongoRepository


interface UserDAO : MongoRepository<User, String> {
	fun findOneByUserName(name: String): User
}