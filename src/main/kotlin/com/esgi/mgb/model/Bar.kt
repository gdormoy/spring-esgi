package com.esgi.mgb.model


import org.springframework.data.geo.Point
import org.springframework.data.mongodb.core.mapping.Document
import javax.persistence.*

@Document
data class Bar(@Id val id: String,
			   var name: String,
			   var address: String,
			   var location: Point,
			   var listOwnerId: MutableList<String>,
			   var listProduct: MutableList<Product> = mutableListOf())
