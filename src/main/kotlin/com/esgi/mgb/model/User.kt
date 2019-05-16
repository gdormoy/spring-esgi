package com.esgi.mgb.model

import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate
import javax.persistence.*
import javax.validation.constraints.Email

@Document
data class User(@Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: String,
                var pseudo: String,
                @Email(message = "Email should be valid") var email: String,
                private var password: String,
                val birthDate: LocalDate,
                var bar: List<Bar>? = mutableListOf())
