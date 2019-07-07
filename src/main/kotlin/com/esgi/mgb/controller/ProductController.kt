package com.esgi.mgb.controller

import com.esgi.mgb.model.Product
import com.esgi.mgb.services.ProductService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
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
	fun insert(@RequestBody promotion: Product): Product = productService.insert(promotion)

	@PutMapping
	fun update(@RequestBody promotion: Product): Product = productService.update(promotion)

	@DeleteMapping("{id}")
	fun deleteById(@PathVariable id: String): Optional<Product> = productService.deleteById(id)
}