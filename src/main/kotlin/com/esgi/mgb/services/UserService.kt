package com.esgi.mgb.services

import com.esgi.mgb.model.User
import com.esgi.mgb.utils.BasicCrud
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
            userDAO.save(obj).apply {
                //update user
                obj.id.let {
                    //if does has Id then
                    barDAO.saveAll(barDAO.findByOwnerId(it).map { it.also { it.owner = this } })//update all his bars
                }
            }
        } else {
            throw object : Exception("The user does not exists") {}
        }
    }

    override fun deleteById(id: String): Optional<User> {
        return userDAO.findById(id).apply {
            this.ifPresent {
                userDAO.delete(it)
            }
        }
    }
}
