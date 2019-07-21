package com.esgi.mgb.controller

import com.esgi.mgb.model.Product
import com.esgi.mgb.security.service.CustomUserDetailsService
import com.esgi.mgb.services.ProductService
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
@WebMvcTest(ProductController::class)
internal class ProductControllerTest {
    @TestConfiguration
    class ProductControllerTestConfig {
        @Bean
        fun productService() = mockk<ProductService>()

        @Bean
        fun customUserDetailsService() = mockk<CustomUserDetailsService>()

        @Bean
        fun passwordEncoderAndMatcher() = mockk<PasswordEncoder>()
    }

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var productService: ProductService

    @Autowired
    private lateinit var customUserDetailsService: CustomUserDetailsService

    @Autowired
    private lateinit var passwordEncoderAndMatcher: PasswordEncoder

    @Test
    fun `it gets all`() {
        val products = listOf(Product(
                id = "123",
                barId = "456",
                name = "789",
                price = 75.0,
                soft = false
        ))

        val pagedResponse = PageImpl(products)

        every { productService.getAll(any()) } returns pagedResponse

        val result = mockMvc.perform(get("/product"))
                .andExpect(status().isOk)
                .andReturn()

        assertEquals("{\n" +
                "  \"content\" : [ {\n" +
                "    \"id\" : \"123\",\n" +
                "    \"name\" : \"789\",\n" +
                "    \"price\" : 75.0,\n" +
                "    \"soft\" : false,\n" +
                "    \"barId\" : \"456\",\n" +
                "    \"listPromotion\" : null\n" +
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

        verify { productService.getAll(any()) }
    }

    @Test
    fun `it gets by id`() {
        val product = Product(
                id = "123",
                barId = "456",
                name = "789",
                price = 75.0,
                soft = false
        )

        every { productService.getById("123") } returns Optional.of(product)

        val result = mockMvc.perform(get("/product/123"))
                .andExpect(status().isOk)
                .andReturn()

        assertEquals("{\n" +
                "  \"id\" : \"123\",\n" +
                "  \"name\" : \"789\",\n" +
                "  \"price\" : 75.0,\n" +
                "  \"soft\" : false,\n" +
                "  \"barId\" : \"456\",\n" +
                "  \"listPromotion\" : null\n" +
                "}", result.response.contentAsString)

        verify { productService.getById("123") }
    }

    @Test
    fun `it inserts`() {
        val product = Product(
                id = "123",
                barId = "456",
                name = "789",
                price = 75.0,
                soft = false
        )

        every { productService.insert(any()) } returns product

        val result = mockMvc.perform(post("/product").contentType(MediaType.APPLICATION_JSON_UTF8).content("{\n" +
                "  \"id\" : \"123\",\n" +
                "  \"barId\" : \"456\",\n" +
                "  \"name\"  : \"789\",\n" +
                "  \"price\" : 75,\n" +
                "  \"soft\" : false\n" +
                "}"))
                .andExpect(status().isCreated)
                .andReturn()

        assertEquals("{\n" +
                "  \"id\" : \"123\",\n" +
                "  \"name\" : \"789\",\n" +
                "  \"price\" : 75.0,\n" +
                "  \"soft\" : false,\n" +
                "  \"barId\" : \"456\",\n" +
                "  \"listPromotion\" : null\n" +
                "}", result.response.contentAsString)

        verify { productService.insert(any()) }
    }

    @Test
    fun `it updates`() {
        val product = Product(
                id = "123",
                barId = "456",
                name = "789",
                price = 75.0,
                soft = false
        )

        every { productService.update(any()) } returns product

        val result = mockMvc.perform(put("/product").contentType(MediaType.APPLICATION_JSON_UTF8).content("{\n" +
                "  \"id\" : \"123\",\n" +
                "  \"barId\" : \"456\",\n" +
                "  \"name\"  : \"789\",\n" +
                "  \"price\" : 75,\n" +
                "  \"soft\" : false\n" +
                "}"))
                .andExpect(status().isOk)
                .andReturn()

        assertEquals("{\n" +
                "  \"id\" : \"123\",\n" +
                "  \"name\" : \"789\",\n" +
                "  \"price\" : 75.0,\n" +
                "  \"soft\" : false,\n" +
                "  \"barId\" : \"456\",\n" +
                "  \"listPromotion\" : null\n" +
                "}", result.response.contentAsString)

        verify { productService.update(any()) }
    }

    @Test
    fun `it deletes by id`() {
        val product = Product(
                id = "123",
                barId = "456",
                name = "789",
                price = 75.0,
                soft = false
        )

        every { productService.deleteById("123") } returns Optional.of(product)

        val result = mockMvc.perform(delete("/product/123").contentType(MediaType.APPLICATION_JSON_UTF8).content("{\n" +
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
        verify { productService.deleteById("123") }
    }
}