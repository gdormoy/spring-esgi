package com.esgi.mgb.controller

import com.esgi.mgb.model.Bar
import com.esgi.mgb.services.BarService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("bar")
class BarController(private val barService: BarService) {


    @GetMapping
    fun getAll(pageable: Pageable): Page<Bar> = barService.getAll(pageable)


    @GetMapping("{id}")
    fun getByIsbn(@PathVariable id: String): Optional<Bar> = barService.getById(id)


    @GetMapping("/byName/{regex}")
    fun getByName(@PathVariable regex: String): List<Bar> = barService.barDAO.findByNameRegex(regex)


    @PostMapping
    fun insert(@RequestBody bar: Bar): Bar = barService.insert(bar)


    @PutMapping
    fun update(@RequestBody bar: Bar): Bar = barService.update(bar)


    @DeleteMapping("{id}")
    fun deleteById(@PathVariable id: String): Optional<Bar> = barService.deleteById(id)
}