package com.esgi.mgb.controller

import com.esgi.mgb.MgbApplication
import org.junit.runner.RunWith
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.mock.mockito.MockBean





@RunWith(SpringRunner::class)
@WebMvcTest(UserController::class)
@ContextConfiguration(classes = [MgbApplication::class])
class UserControllerTest {
    @Autowired
    private val mvc: MockMvc? = null
//
//    @MockBean
//    private val userRepository: UserRepository? = null
}