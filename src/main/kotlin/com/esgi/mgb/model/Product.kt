package com.esgi.mgb.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document


@Document
data class Product(@Id val id: String,
				   var name: String,
				   var price: Double,
				   var soft: Boolean,
				   var barId: String,
				   var listPromotion: MutableList<Promotion>? = null)