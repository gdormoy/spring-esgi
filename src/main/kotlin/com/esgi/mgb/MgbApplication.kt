package com.esgi.mgb

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MgbApplication

fun main(args: Array<String>) {
    runApplication<MgbApplication>(*args)
}
