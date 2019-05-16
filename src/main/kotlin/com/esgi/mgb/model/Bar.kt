package com.esgi.mgb.model

import org.springframework.data.mongodb.core.mapping.Document
import javax.persistence.*


@Document
data class Bar(@Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: String,
               var name: String,
               var address: String,
               var owner: User)