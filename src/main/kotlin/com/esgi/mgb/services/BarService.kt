package com.esgi.mgb.services

import com.esgi.mgb.dao.BarDAO
import com.esgi.mgb.dao.ProductDAO
import com.esgi.mgb.dao.UserDAO
import com.esgi.mgb.model.Bar
import com.esgi.mgb.utils.BasicCrud
import javassist.NotFoundException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.*


@Service
class BarService(val barDAO: BarDAO, private val productDAO: ProductDAO) : BasicCrud<String, Bar> {
    override fun getAll(pageable: Pageable): Page<Bar> = barDAO.findAll(pageable)

    override fun getById(id: String): Optional<Bar> = barDAO.findById(id)

	override fun insert(obj: Bar): Bar = insertBarAndReInsertItProduct(obj)

	@Throws(NotFoundException::class)
    override fun update(obj: Bar): Bar {
		return if (barDAO.existsById(obj.id)) {
			barDAO.save(insertBarAndReInsertItProduct(obj))
		} else {
			throw object : NotFoundException("${Bar::class}: $obj not found") {}
		}
    }

    override fun deleteById(id: String): Optional<Bar> {
        return barDAO.findById(id).apply {
			this.ifPresent { barDAO.delete(it) }
		}
	}

	private fun insertBarAndReInsertItProduct(bar: Bar): Bar {
		return barDAO.insert(bar.apply {
			(productDAO.findByBarId(bar.id).forEach {
				this.listProduct.add(it)
			})
		})
    }
}