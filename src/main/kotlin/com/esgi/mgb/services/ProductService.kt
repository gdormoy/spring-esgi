package com.esgi.mgb.services

import com.esgi.mgb.dao.ProductDAO
import com.esgi.mgb.dao.PromotionDAO
import com.esgi.mgb.model.Product
import com.esgi.mgb.utils.BasicCrud
import javassist.NotFoundException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.*


@Service
class ProductService(val productDAO: ProductDAO, private val promotionDAO: PromotionDAO) : BasicCrud<String, Product> {
	override fun getAll(pageable: Pageable): Page<Product> = productDAO.findAll(pageable)

	override fun getById(id: String): Optional<Product> = productDAO.findById(id)

	override fun insert(obj: Product): Product = insertProductAndReInsertItPromotion(obj)

	@Throws(NotFoundException::class)
	override fun update(obj: Product): Product {
		return if (productDAO.existsById(obj.id)) {
			productDAO.save(insertProductAndReInsertItPromotion(obj))
		} else {
			throw object : NotFoundException("${Product::class}: $obj not found") {}
		}
	}

	override fun deleteById(id: String): Optional<Product> {
		return productDAO.findById(id).apply {
			this.ifPresent { productDAO.delete(it) }
		}
	}

	private fun insertProductAndReInsertItPromotion(product: Product): Product {
		return productDAO.insert(product.apply {
			(promotionDAO.findByListProductId(product.id).forEach {
				if (this.listPromotion == null) this.listPromotion = mutableListOf()
				this.listPromotion?.add(it)
			})
		})
	}
}