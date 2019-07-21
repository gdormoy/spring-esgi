package com.esgi.mgb.model

import org.springframework.data.mongodb.core.mapping.Document
import javax.persistence.GeneratedValue
import javax.persistence.Id


@Document
data class Promotion(@Id @GeneratedValue val id: String,
					 var name: String,
					 var reduceAmount: Float,
					 var listProductId: MutableList<String>)