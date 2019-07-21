package com.esgi.mgb.controller

import com.esgi.mgb.model.User
import com.esgi.mgb.services.UserService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.util.*


@RestController
@RequestMapping("user")
class UserController(private val userService: UserService) {
    @GetMapping
    fun getAll(pageable: Pageable): Page<User> = userService.getAll(pageable)

    @GetMapping("{id}")
    fun getById(@PathVariable id: String): Optional<User> = userService.getById(id)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun insert(@RequestBody user: User): User = userService.insert(user)

    @PutMapping
    fun update(@RequestBody user: User): User = userService.update(user)

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun deleteById(@PathVariable id: String) {
        userService.deleteById(id)
    }
}