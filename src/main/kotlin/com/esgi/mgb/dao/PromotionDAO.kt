package com.esgi.mgb.dao

import com.esgi.mgb.model.Promotion
import org.springframework.data.mongodb.repository.MongoRepository


interface PromotionDAO : MongoRepository<Promotion, String> {
	fun findByListProductId(id: String): List<Promotion>
	fun findByNameRegex(name: String): List<Promotion>
}