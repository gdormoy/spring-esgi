package com.esgi.mgb

import com.esgi.mgb.model.Bar
import com.esgi.mgb.model.User
import com.esgi.mgb.dao.BarDAO
import com.esgi.mgb.dao.UserDAO
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
        val toto = userDAO.insert(User(id = "0",
                pseudo = "Toto",
                email = "toto@gmail.com",
                password = "",
                birthDate = "20-09-1997".toLocalDate()))
        val tata = userDAO.insert(User(id = "1",
                pseudo = "Tata",
                email = "tata@gmail.com",
                password = "",
                birthDate = "03-01-1980".toLocalDate()))

        val books = listOf(
                Bar(id = "0", name = "Les Rattrapages", address = "Jussieu", owner = toto),
                Bar(id = "1", name = "Nouvel institue", address = "Jussieu", owner = tata)
        )
        barDAO.insert(books)
    }

    private fun cleanCollections() {
        userDAO.deleteAll()
        barDAO.deleteAll()
    }
}

fun main(args: Array<String>) {
    runApplication<MgbApplication>(*args)
}
