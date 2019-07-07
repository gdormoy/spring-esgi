package com.esgi.mgb.controller

import com.esgi.mgb.model.Promotion
import com.esgi.mgb.services.PromotionService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("promotion")
class PromotionController(private val promotionService: PromotionService) {
	@GetMapping
	fun getAll(pageable: Pageable): Page<Promotion> = promotionService.getAll(pageable)

	@GetMapping("{id}")
	fun getById(@PathVariable id: String): Optional<Promotion> = promotionService.getById(id)

	@PostMapping
	fun insert(@RequestBody promotion: Promotion): Promotion = promotionService.insert(promotion)

	@PutMapping
	fun update(@RequestBody promotion: Promotion): Promotion = promotionService.update(promotion)

	@DeleteMapping("{id}")
	fun deleteById(@PathVariable id: String): Optional<Promotion> = promotionService.deleteById(id)
}