package com.esgi.mgb.services


import com.esgi.mgb.dao.UserDAO
import com.esgi.mgb.model.User
import com.esgi.mgb.utils.toLocalDate
import io.mockk.impl.annotations.MockK
import org.junit.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

open class UserServiceTest {

    private lateinit var userService : UserService
    @MockK
    private val userDAO: UserDAO? = null

    // MockK
    @Test
    public fun testUpdateUserShouldWork() : Unit {
        // given
        val Sharko = User(id = "0",
                name = "unknow",
                pseudo = "Sharko",
                email = "unknow@gmail.com",
                password = "",
                birthDate = "20-09-1997".toLocalDate(),
                listBar = null)

        var res = mvc?.perform(MockMvcRequestBuilders.put("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))?.andExpect(status().isOk)?.andReturn()

    }
}