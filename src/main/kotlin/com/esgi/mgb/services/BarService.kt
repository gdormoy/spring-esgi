package com.esgi.mgb.services

import com.esgi.mgb.dao.BarDAO
import com.esgi.mgb.dao.UserDAO
import com.esgi.mgb.model.Bar
import com.esgi.mgb.utils.BasicCrud
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.*


@Service
class BarService(val barDAO: BarDAO, private val userDAO: UserDAO) : BasicCrud<String, Bar> {
    override fun getAll(pageable: Pageable): Page<Bar> = barDAO.findAll(pageable)

    override fun getById(id: String): Optional<Bar> = barDAO.findById(id)

    override fun insert(obj: Bar): Bar = barDAO.insert(obj.apply { this.owner = userDAO.findById(obj.owner.id).get() })

    @Throws(Exception::class)
    override fun update(obj: Bar): Bar {
        return if (barDAO.existsById(obj.id)) {
            barDAO.save(obj.apply { this.owner = userDAO.findById(obj.owner.id).get() })
        } else throw object : Exception("Bar not found") {}
    }

    override fun deleteById(id: String): Optional<Bar> {
        return barDAO.findById(id).apply {
            this.ifPresent {
                barDAO.delete(it)
            }
        }
    }
}