package com.esgi.mgb.controller

import com.esgi.mgb.model.Product
import com.esgi.mgb.services.ProductService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("product")
class ProductController(private val productService: ProductService) {
	@GetMapping
	fun getAll(pageable: Pageable): Page<Product> = productService.getAll(pageable)

	@GetMapping("{id}")
	fun getById(@PathVariable id: String): Optional<Product> = productService.getById(id)

	@PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun insert(@RequestBody product: Product): Product = productService.insert(product)

	@PutMapping
	fun update(@RequestBody product: Product): Product = productService.update(product)

	@DeleteMapping("{id}")
	@ResponseStatus(HttpStatus.ACCEPTED)
	fun deleteById(@PathVariable id: String) {
		productService.deleteById(id)
	}
}