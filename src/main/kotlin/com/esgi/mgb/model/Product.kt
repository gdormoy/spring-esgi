package com.esgi.mgb.model

import org.springframework.data.annotation.Id

data class Product(@Id val id: String,
                   var name: String,
                   var price: Double,
                   var soft: Boolean,
                   var barId : List<String>,
                   var listPromotion: List<Promotion>? = null)