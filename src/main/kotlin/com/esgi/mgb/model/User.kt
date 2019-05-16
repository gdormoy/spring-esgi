package com.esgi.mgb.model

import javax.persistence.*

@Document
data class User(@Id val id: String,
				var pseudo: String,
				var name: String,
				@Email(regexp = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})")
				var email: String,
				private var password: String,
				val birthDate: LocalDate,
				var listBar: MutableList<Bar>? = null)