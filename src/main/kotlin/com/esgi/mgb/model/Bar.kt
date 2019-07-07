package com.esgi.mgb.model

import com.esgi.mgb.utils.Location
import org.springframework.data.mongodb.core.mapping.Document
import javax.persistence.*


@Document
data class Bar(@Id val id: String,
			   var name: String,
			   var address: String,
			   var location: Location,
			   var listOwnerId: MutableList<String>,
			   var listProduct: MutableList<Product> = mutableListOf())