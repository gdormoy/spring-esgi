package com.esgi.mgb.controller

import com.esgi.mgb.model.User
import com.esgi.mgb.security.service.CustomUserDetailsService
import com.esgi.mgb.services.UserService
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.data.domain.PageImpl
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import java.time.LocalDate
import java.util.*

@ExtendWith(SpringExtension::class)
@WebMvcTest(UserController::class)
internal class UserControllerTest {
    @TestConfiguration
    class UserControllerTestConfig {
        @Bean
        fun userService() = mockk<UserService>()

        @Bean
        fun customUserDetailsService() = mockk<CustomUserDetailsService>()

        @Bean
        fun passwordEncoderAndMatcher() = mockk<PasswordEncoder>()
    }

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var userService: UserService

    @Autowired
    private lateinit var customUserDetailsService: CustomUserDetailsService

    @Autowired
    private lateinit var passwordEncoderAndMatcher: PasswordEncoder

    @Test
    fun `it gets all users`() {
        val users = listOf(User(
                birthDate = LocalDate.now(),
                email = "test@test.fr",
                firstname = "test",
                lastname = "test",
                passWord = "test",
                userName = "test"
        ))

        val pagedResponse = PageImpl(users)

        every { userService.getAll(any()) } returns pagedResponse

        val result = mockMvc.perform(get("/user"))
                .andExpect(status().isOk)
                .andReturn()

        assertEquals("{\n" +
                "  \"content\" : [ {\n" +
                "    \"firstname\" : \"test\",\n" +
                "    \"lastname\" : \"test\",\n" +
                "    \"email\" : \"test@test.fr\",\n" +
                "    \"passWord\" : \"test\",\n" +
                "    \"userName\" : \"test\",\n" +
                "    \"birthDate\" : \"" + LocalDate.now().toString() + "\",\n" +
                "    \"id\" : \"\",\n" +
                "    \"listBar\" : null,\n" +
                "    \"roles\" : [ ],\n" +
                "    \"accountNonExpired\" : true,\n" +
                "    \"accountNonLocked\" : true,\n" +
                "    \"credentialsNonExpired\" : true,\n" +
                "    \"enabled\" : true\n" +
                "  } ],\n" +
                "  \"pageable\" : \"INSTANCE\",\n" +
                "  \"totalPages\" : 1,\n" +
                "  \"totalElements\" : 1,\n" +
                "  \"last\" : true,\n" +
                "  \"size\" : 0,\n" +
                "  \"number\" : 0,\n" +
                "  \"sort\" : {\n" +
                "    \"sorted\" : false,\n" +
                "    \"unsorted\" : true,\n" +
                "    \"empty\" : true\n" +
                "  },\n" +
                "  \"first\" : true,\n" +
                "  \"numberOfElements\" : 1,\n" +
                "  \"empty\" : false\n" +
                "}", result.response.contentAsString)

        verify { userService.getAll(any()) }
    }

    @Test
    fun `it gets by id`() {
        val user = User(
                id = "123",
                birthDate = LocalDate.now(),
                email = "test@test.fr",
                firstname = "test",
                lastname = "test",
                passWord = "test",
                userName = "test"
        )

        every { userService.getById("123") } returns Optional.of(user)

        val result = mockMvc.perform(get("/user/123"))
                .andExpect(status().isOk)
                .andReturn()

        assertEquals("{\n" +
                "  \"firstname\" : \"test\",\n" +
                "  \"lastname\" : \"test\",\n" +
                "  \"email\" : \"test@test.fr\",\n" +
                "  \"passWord\" : \"test\",\n" +
                "  \"userName\" : \"test\",\n" +
                "  \"birthDate\" : \"" + LocalDate.now().toString() + "\",\n" +
                "  \"id\" : \"123\",\n" +
                "  \"listBar\" : null,\n" +
                "  \"roles\" : [ ],\n" +
                "  \"accountNonExpired\" : true,\n" +
                "  \"accountNonLocked\" : true,\n" +
                "  \"credentialsNonExpired\" : true,\n" +
                "  \"enabled\" : true\n" +
                "}", result.response.contentAsString)

        verify { userService.getById("123") }
    }

    @Test
    fun `it inserts`() {
        val user = User(
                id = "123",
                birthDate = LocalDate.now(),
                email = "test@test.fr",
                firstname = "test",
                lastname = "test",
                passWord = "test",
                userName = "test"
        )

        every { userService.insert(any()) } returns user

        val result = mockMvc.perform(post("/user").contentType(MediaType.APPLICATION_JSON_UTF8).content("{\n" +
                "  \"firstname\" : \"test\",\n" +
                "  \"lastname\" : \"test\",\n" +
                "  \"email\" : \"test@test.fr\",\n" +
                "  \"passWord\" : \"test\",\n" +
                "  \"userName\" : \"test\",\n" +
                "  \"birthDate\" : \"" + LocalDate.now().toString() + "\",\n" +
                "  \"id\" : \"123\",\n" +
                "  \"listBar\" : null,\n" +
                "  \"roles\" : [ ],\n" +
                "  \"accountNonExpired\" : true,\n" +
                "  \"accountNonLocked\" : true,\n" +
                "  \"credentialsNonExpired\" : true,\n" +
                "  \"enabled\" : true\n" +
                "}"))
                .andExpect(status().isCreated)
                .andReturn()

        assertEquals("{\n" +
                "  \"firstname\" : \"test\",\n" +
                "  \"lastname\" : \"test\",\n" +
                "  \"email\" : \"test@test.fr\",\n" +
                "  \"passWord\" : \"test\",\n" +
                "  \"userName\" : \"test\",\n" +
                "  \"birthDate\" : \"" + LocalDate.now().toString() + "\",\n" +
                "  \"id\" : \"123\",\n" +
                "  \"listBar\" : null,\n" +
                "  \"roles\" : [ ],\n" +
                "  \"accountNonExpired\" : true,\n" +
                "  \"accountNonLocked\" : true,\n" +
                "  \"credentialsNonExpired\" : true,\n" +
                "  \"enabled\" : true\n" +
                "}", result.response.contentAsString)

        verify { userService.insert(any()) }
    }

    @Test
    fun `it updates`() {
        val user = User(
                id = "123",
                birthDate = LocalDate.now(),
                email = "test@test.fr",
                firstname = "test",
                lastname = "test",
                passWord = "test",
                userName = "test"
        )

        every { userService.update(any()) } returns user

        val result = mockMvc.perform(put("/user").contentType(MediaType.APPLICATION_JSON_UTF8).content("{\n" +
                "  \"firstname\" : \"test\",\n" +
                "  \"lastname\" : \"test\",\n" +
                "  \"email\" : \"test@test.fr\",\n" +
                "  \"passWord\" : \"test\",\n" +
                "  \"userName\" : \"test\",\n" +
                "  \"birthDate\" : \"" + LocalDate.now().toString() + "\",\n" +
                "  \"id\" : \"123\",\n" +
                "  \"listBar\" : null,\n" +
                "  \"roles\" : [ ],\n" +
                "  \"accountNonExpired\" : true,\n" +
                "  \"accountNonLocked\" : true,\n" +
                "  \"credentialsNonExpired\" : true,\n" +
                "  \"enabled\" : true\n" +
                "}"))
                .andExpect(status().isOk)
                .andReturn()

        assertEquals("{\n" +
                "  \"firstname\" : \"test\",\n" +
                "  \"lastname\" : \"test\",\n" +
                "  \"email\" : \"test@test.fr\",\n" +
                "  \"passWord\" : \"test\",\n" +
                "  \"userName\" : \"test\",\n" +
                "  \"birthDate\" : \"" + LocalDate.now().toString() + "\",\n" +
                "  \"id\" : \"123\",\n" +
                "  \"listBar\" : null,\n" +
                "  \"roles\" : [ ],\n" +
                "  \"accountNonExpired\" : true,\n" +
                "  \"accountNonLocked\" : true,\n" +
                "  \"credentialsNonExpired\" : true,\n" +
                "  \"enabled\" : true\n" +
                "}", result.response.contentAsString)

        verify { userService.update(any()) }
    }

    @Test
    fun `it deletes by id`() {
        val user = User(
                id = "123",
                birthDate = LocalDate.now(),
                email = "test@test.fr",
                firstname = "test",
                lastname = "test",
                passWord = "test",
                userName = "test"
        )

        every { userService.deleteById("123") } returns Optional.of(user)

        val result = mockMvc.perform(delete("/user/123").contentType(MediaType.APPLICATION_JSON_UTF8).content("{\n" +
                "  \"firstname\" : \"test\",\n" +
                "  \"lastname\" : \"test\",\n" +
                "  \"email\" : \"test@test.fr\",\n" +
                "  \"passWord\" : \"test\",\n" +
                "  \"userName\" : \"test\",\n" +
                "  \"birthDate\" : \"" + LocalDate.now().toString() + "\",\n" +
                "  \"id\" : \"123\",\n" +
                "  \"listBar\" : null,\n" +
                "  \"roles\" : [ ],\n" +
                "  \"accountNonExpired\" : true,\n" +
                "  \"accountNonLocked\" : true,\n" +
                "  \"credentialsNonExpired\" : true,\n" +
                "  \"enabled\" : true\n" +
                "}"))
                .andExpect(status().isAccepted)
                .andReturn()

        assertEquals("", result.response.contentAsString)
        verify { userService.deleteById("123") }
    }
}