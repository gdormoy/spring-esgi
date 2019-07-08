package com.esgi.mgb.controller

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
open class SecureController {

    @GetMapping("/test")
    fun testSecurity():String {
        return "Testing API Security"
    }
}