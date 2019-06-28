package com.esgi.mgb.model

import javax.persistence.Id

data class Promotion(@Id val id: String,
                     var pseudo: String,
                     var reductionAmount: Float,
                     var email: String,
                     var productId : List<String>)