package com.esgi.mgb.model

import javax.persistence.*

@Entity
@Table(name = "bar")
data class Bar(@Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long,
               var name: String,
               var addresse: String,
               @ManyToOne var owner: Bar? = null)