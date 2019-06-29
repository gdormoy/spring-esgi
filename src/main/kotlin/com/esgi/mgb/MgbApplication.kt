package com.esgi.mgb

import com.esgi.mgb.model.Bar
import com.esgi.mgb.model.User
import com.esgi.mgb.dao.BarDAO
import com.esgi.mgb.dao.UserDAO
import com.esgi.mgb.utils.Location
import com.esgi.mgb.utils.toLocalDate
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MgbApplication(private val barDAO: BarDAO, private val userDAO: UserDAO) : ApplicationRunner {
    /* This will run after springboot full load*/
    override fun run(args: ApplicationArguments?) {
        if (barDAO.count() < 1) this.createBar()
    }

    private fun createBar() {
        this.cleanCollections()

        val bars = listOf(
                Bar(id = "0",
                        name = "Les Rattrapages BIS",
                        address = "14 rue des fosses saint Bernard Paris",
                        listOwnerId = mutableListOf("0"),
                        location = Location(latitude = 48.8487364, longitude = 2.355045)),
                Bar(id = "1",
                        name = "Nouvel institue",
                        address = "34 Rue de Clignancourt, Paris 18e Arrondissement, Île-de-France, France",
                        listOwnerId = mutableListOf("1"),
						location = Location(latitude = 48.8863, longitude = 2.3476100000000315))
                )
        barDAO.insert(bars)

        val lesRattrapages = userDAO.insert(User(id = "0",
                name = "unknow",
                pseudo = "unknow",
                email = "unknow@gmail.com",
                password = "",
                birthDate = "20-09-1997".toLocalDate(),
                listBar = mutableListOf(bars[0])))
        val amedeEscale = userDAO.insert(User(id = "1",
                name = "Amédé",
                pseudo = "Amédé",
                email = "amede@gmail.com",
                password = "",
                birthDate = "03-01-1980".toLocalDate(),
                listBar = mutableListOf(bars[1])))
    }

    private fun cleanCollections() {
        userDAO.deleteAll()
        barDAO.deleteAll()
    }
}

fun main(args: Array<String>) {
    runApplication<MgbApplication>(*args)
}
