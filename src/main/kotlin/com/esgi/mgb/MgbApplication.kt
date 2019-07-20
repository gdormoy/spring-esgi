package com.esgi.mgb

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MgbApplication

// Simple user fixture for test : username spdeepak password spdeepak
fun main(args: Array<String>) {
    runApplication<MgbApplication>(*args)
}
