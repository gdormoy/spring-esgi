package com.esgi.mgb.services

import com.esgi.mgb.dao.BarDAO
import com.esgi.mgb.dao.UserDAO
import com.esgi.mgb.model.Bar
import com.esgi.mgb.model.User
import com.esgi.mgb.utils.BasicCrud
import javassist.NotFoundException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.*


@Service
class UserService(private val userDAO: UserDAO, private val barDAO: BarDAO) : BasicCrud<String, User> {
    override fun getAll(pageable: Pageable): Page<User> = userDAO.findAll(pageable)

    override fun getById(id: String): Optional<User> = userDAO.findById(id)

    override fun insert(obj: User): User = userDAO.insert(obj)

    @Throws(Exception::class)
    override fun update(obj: User): User {
        return if (userDAO.existsById(obj.id)) { //check if user exists because the save method will insert a record if does not exists
			userDAO.save(insertUserAndReInsertItBar(obj))
        } else {
			throw object : NotFoundException("${User::class}: $obj not found") {}
        }
    }

    override fun deleteById(id: String): Optional<User> {
        return userDAO.findById(id).apply {
            this.ifPresent {
                userDAO.delete(it)
            }
        }
    }

	private fun insertUserAndReInsertItBar(user: User): User {
		return userDAO.insert(user.apply {
			(barDAO.findByListOwnerId(user.id).forEach {
				if (this.listBar == null) this.listBar = mutableListOf()
				this.listBar?.add(it)
			})
		})
	}
}
