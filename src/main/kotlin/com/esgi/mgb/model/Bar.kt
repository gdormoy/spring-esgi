package com.esgi.mgb.model

import org.springframework.data.mongodb.core.mapping.Document
import javax.persistence.*


@Document
data class Bar(@Id val id: String,
               var name: String,
               var address: String,
               var ownerId : List<String>,
               var product: List<Product>)