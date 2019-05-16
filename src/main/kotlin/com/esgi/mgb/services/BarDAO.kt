package com.esgi.mgb.services

import com.esgi.mgb.model.Bar
import com.esgi.mgb.model.User
import org.springframework.data.mongodb.repository.MongoRepository

interface BarDAO : MongoRepository<Bar, String> {
    fun findByOwnerId(id: String): List<Bar>
    fun findByNameRegex(name: String): List<Bar>
}