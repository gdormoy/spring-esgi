package com.esgi.mgb.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun dateFormat(): DateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")

fun String.toLocalDate(): LocalDate = LocalDate.parse(this, dateFormat())