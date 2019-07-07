package com.esgi.mgb.controller

import com.esgi.mgb.model.Bar
import com.esgi.mgb.model.Product
import com.esgi.mgb.services.BarService
import com.esgi.mgb.services.ProductService
import com.esgi.mgb.utils.isInRangeOf
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.geo.*
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("bar")
class BarController(private val barService: BarService, private val productService: ProductService) {

    @GetMapping
    fun getAll(pageable: Pageable): Page<Bar> = barService.getAll(pageable)


    @GetMapping("{id}")
    fun getByIsbn(@PathVariable id: String): Optional<Bar> = barService.getById(id)


    @GetMapping("/byName/{regex}")
    fun getByName(@PathVariable regex: String): List<Bar> = barService.barDAO.findByNameRegex(regex)

	@GetMapping("/closeTo/{lat}:{lng}&{maxRangeMeters}")
	fun getByProximity(@PathVariable lat: Double, @PathVariable lng: Double, @PathVariable maxRangeMeters: Int)
			: List<Bar> {
		return barService.barDAO.findAll().filter {
			it.location.isInRangeOf(Circle(Point(lat, lng), maxRangeMeters.toDouble()))
		}
	}

	@GetMapping("/withProducts/{namesProducts}")
	fun getByProductsName(@PathVariable namesProducts: List<String>): List<Bar> {
		val listProducts = mutableListOf<Product>()
		for (name in namesProducts) listProducts.addAll(productService.productDAO.findByNameRegex(name))
		return barService.barDAO.findByListProductContains(listProducts)
	}

	@GetMapping("/closeToAnd/{lat}:{lng}&{maxRangeMeters}/withProducts/{namesProducts}")
	fun getByProximityAndProducts(@PathVariable lat: Double, @PathVariable lng: Double,
								  @PathVariable maxRangeMeters: Int,
								  @PathVariable namesProducts: List<String>): List<Bar> {
		val listBar = getByProximity(lat, lng, maxRangeMeters)
		listBar.filter {
			val listProduct = it.listProduct.filter { namesProducts.contains(it.name) }
			listProduct.size == namesProducts.size
		}
		return listBar
	}

    @PostMapping
    fun insert(@RequestBody bar: Bar): Bar = barService.insert(bar)


    @PutMapping
    fun update(@RequestBody bar: Bar): Bar = barService.update(bar)


    @DeleteMapping("{id}")
    fun deleteById(@PathVariable id: String): Optional<Bar> = barService.deleteById(id)
}