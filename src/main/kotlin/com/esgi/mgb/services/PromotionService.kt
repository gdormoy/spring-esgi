package com.esgi.mgb.services

import com.esgi.mgb.dao.PromotionDAO
import com.esgi.mgb.model.Promotion
import com.esgi.mgb.utils.BasicCrud
import javassist.NotFoundException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.*


@Service
class PromotionService(val promotionDAO: PromotionDAO) : BasicCrud<String, Promotion> {
	override fun getAll(pageable: Pageable): Page<Promotion> = promotionDAO.findAll(pageable)

	override fun getById(id: String): Optional<Promotion> = promotionDAO.findById(id)

	override fun insert(obj: Promotion): Promotion = promotionDAO.insert(obj)

	@Throws(NotFoundException::class)
	override fun update(obj: Promotion): Promotion {
		return if (promotionDAO.existsById(obj.id)) {
			promotionDAO.save(promotionDAO.insert(obj))
		} else {
			throw object : NotFoundException("${Promotion::class}: $obj not found") {}
		}
	}

	override fun deleteById(id: String): Optional<Promotion> {
		return promotionDAO.findById(id).apply {
			this.ifPresent { promotionDAO.delete(it) }
		}
	}
}