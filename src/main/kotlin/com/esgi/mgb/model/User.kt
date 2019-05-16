package com.esgi.mgb.model

import javax.persistence.*
import javax.validation.constraints.Email

@Entity
@Table(name = "user")
data class User(@Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long,
                var pseudo: String,
                @Email(message = "Email should be valid") var email: String,
                private var password: String,
                var year: Int,
                @OneToMany(mappedBy = "pub") var bar: List<Bar>? = mutableListOf())
