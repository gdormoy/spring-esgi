package com.esgi.mgb

import com.esgi.mgb.dao.BarDAO
import com.esgi.mgb.dao.ProductDAO
import com.esgi.mgb.dao.RoleDAO
import com.esgi.mgb.dao.UserDAO
import com.esgi.mgb.model.Bar
import com.esgi.mgb.model.Product
import com.esgi.mgb.model.User
import com.esgi.mgb.utils.Location
import com.esgi.mgb.utils.toLocalDate
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MgbApplication(private val barDAO: BarDAO,
                     private val userDAO: UserDAO,
                     private val productDAO: ProductDAO,
                     private val roleDAO: RoleDAO)
    : ApplicationRunner {
    /* This will run after springboot full load*/
    override fun run(args: ApplicationArguments?) {
        this.createBar()
    }

    private fun createBar() {
        this.cleanCollections()

        val product = listOf(
                Product(
                        "0",
                        "Chouffe",
                        6.0,
                        false,
                        "0",
                        null
                ),
                Product(
                        "1",
                        "Mojito",
                        1.0,
                        false,
                        "1",
                        null
                )
        )
        productDAO.insert(product)

        val bars = listOf(
                Bar(id = "0",
                        name = "Les Rattrapages",
                        address = "14 rue des fosses saint Bernard Paris",
                        listOwnerId = mutableListOf("0"),
                        location = Location(latitude = 48.8487364, longitude = 2.355045),
                        listProduct = mutableListOf(product[0])),
                Bar(id = "1",
                        name = "L'Escale",
                        address = "34 Rue de Clignancourt, Paris 18e Arrondissement, Île-de-France, France",
                        listOwnerId = mutableListOf("1"),
                        location = Location(latitude = 48.8863, longitude = 2.3476100000000315),
                        listProduct = mutableListOf(product[1]))
                )
        barDAO.insert(bars)

        // password for testing = spdeepak
        val lesRattrapages = userDAO.insert(User(id = "0",
                firstname = "unknow",
                lastname = "rattrape",
                userName = "Sharko",
                email = "unknow@gmail.com",
                passWord = "\$2a\$10\$2nU5xMCyzPMeNiGQsrjzQeWNcHf9NjtGzVFgy6kVRcZlLh/ABTgZW",
                birthDate = "20-09-1997".toLocalDate(),
                listBar = mutableListOf(bars[0])))
        val amedeEscale = userDAO.insert(User(id = "1",
                firstname =  "Amédé",
                lastname = "Escale",
                userName = "Amédé",
                email = "amede@gmail.com",
                passWord = "test",
                birthDate = "03-01-1980".toLocalDate(),
                listBar = mutableListOf(bars[1])))

        userDAO.insert(User(id = "2",
                firstname = "René",
                lastname = "whocare",
                userName = "Gros Réré",
                email = "réré77890@gmail.com",
                passWord = "test",
                birthDate = "20-09-1947".toLocalDate()))
    }

    private fun cleanCollections() {
        userDAO.deleteAll()
        barDAO.deleteAll()
        productDAO.deleteAll()
        roleDAO.deleteAll()
    }
}

// Simple user fixture for test : username spdeepak password spdeepak
fun main(args: Array<String>) {
    runApplication<MgbApplication>(*args)
}
