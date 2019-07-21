package com.esgi.mgb.dao

import com.esgi.mgb.model.Bar
import com.esgi.mgb.model.Product
import org.springframework.data.mongodb.repository.MongoRepository


interface BarDAO : MongoRepository<Bar, String> {

	fun findByListOwnerId(id: String): List<Bar>
    fun findByNameRegex(name: String): List<Bar>
	fun findByListProductContains(listProduct: MutableList<Product>): List<Bar>
}
