package com.esgi.mgb.dao

import com.esgi.mgb.model.Product
import org.springframework.data.mongodb.repository.MongoRepository


interface ProductDAO : MongoRepository<Product, String> {

	fun findByBarId(id: String): List<Product>
	fun findByNameRegex(name: String): List<Product>
}