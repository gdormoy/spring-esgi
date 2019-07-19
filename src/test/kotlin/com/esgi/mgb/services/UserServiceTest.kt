package com.esgi.mgb.services


import com.esgi.mgb.dao.BarDAO
import com.esgi.mgb.dao.UserDAO
import com.esgi.mgb.model.User
import com.esgi.mgb.utils.toLocalDate
import io.mockk.every
import io.mockk.mockk
import org.junit.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

open class UserServiceTest {

    private val userDAO = mockk<UserDAO> {
        every { existsById("0") } returns true
        every { save(any()) }
    }
    private val barDAO = mockk<BarDAO>(relaxed = true)

    private val userService = UserService(userDAO, barDAO)

    // MockK
    @Test
    fun testUpdateUserShouldWork(): Unit {
        // given
        userService.insert(User(id = "0",
                firstname = "unknow",
                lastname = "rattrape",
                userName = "Sharko",
                email = "unknow@gmail.com",
                passWord = "\$2a\$10\$2nU5xMCyzPMeNiGQsrjzQeWNcHf9NjtGzVFgy6kVRcZlLh/ABTgZW",
                birthDate = "20-09-1997".toLocalDate(),
                listBar = null))

        val userUpdate = User(id = "0",
                firstname = "unknow",
                lastname = "rattrape",
                userName = "Sharko",
                email = "unknow@gmail.com",
                passWord = "toto",
                birthDate = "20-09-1997".toLocalDate(),
                listBar = null)

        val res = userService.update(userUpdate)

        assert(res.passWord == userUpdate.passWord)

//        var res = mvc?.perform(MockMvcRequestBuilders.put("/user")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(content))?.andExpect(status().isOk)?.andReturn()

    }
}