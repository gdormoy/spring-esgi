package com.esgi.mgb.controller

import com.esgi.mgb.model.Product
import com.esgi.mgb.model.Promotion
import com.esgi.mgb.security.service.CustomUserDetailsService
import com.esgi.mgb.services.ProductService
import com.esgi.mgb.services.PromotionService
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
@WebMvcTest(PromotionController::class)
internal class PromotionControllerTest {
    @TestConfiguration
    class PromotionControllerTestConfig {
        @Bean
        fun promotionService() = mockk<PromotionService>()

        @Bean
        fun customUserDetailsService() = mockk<CustomUserDetailsService>()

        @Bean
        fun passwordEncoderAndMatcher() = mockk<PasswordEncoder>()
    }

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var promotionService: PromotionService

    @Autowired
    private lateinit var customUserDetailsService: CustomUserDetailsService

    @Autowired
    private lateinit var passwordEncoderAndMatcher: PasswordEncoder

    @Test
    fun `it gets all`() {
        val promotions = listOf(Promotion(
                id = "123",
                name = "789",
                reduceAmount = 15f,
                listProductId = mutableListOf("456")
        ))

        val pagedResponse = PageImpl(promotions)

        every { promotionService.getAll(any()) } returns pagedResponse

        val result = mockMvc.perform(get("/promotion"))
                .andExpect(status().isOk)
                .andReturn()

        assertEquals("{\n" +
                "  \"content\" : [ {\n" +
                "    \"id\" : \"123\",\n" +
                "    \"name\" : \"789\",\n" +
                "    \"reduceAmount\" : 15.0,\n" +
                "    \"listProductId\" : [ \"456\" ]\n" +
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

        verify { promotionService.getAll(any()) }
    }

    @Test
    fun `it gets by id`() {
        val promotion = Promotion(
                id = "123",
                name = "789",
                reduceAmount = 15f,
                listProductId = mutableListOf("456")
        )

        every { promotionService.getById("123") } returns Optional.of(promotion)

        val result = mockMvc.perform(get("/promotion/123"))
                .andExpect(status().isOk)
                .andReturn()

        assertEquals("{\n" +
                "  \"id\" : \"123\",\n" +
                "  \"name\" : \"789\",\n" +
                "  \"reduceAmount\" : 15.0,\n" +
                "  \"listProductId\" : [ \"456\" ]\n" +
                "}", result.response.contentAsString)

        verify { promotionService.getById("123") }
    }

    @Test
    fun `it inserts`() {
        val promotion = Promotion(
                id = "123",
                name = "789",
                reduceAmount = 15f,
                listProductId = mutableListOf("456")
        )

        every { promotionService.insert(any()) } returns promotion

        val result = mockMvc.perform(post("/promotion").contentType(MediaType.APPLICATION_JSON_UTF8).content("{\n" +
                "  \"id\" : \"123\",\n" +
                "  \"name\" : \"789\",\n" +
                "  \"reduceAmount\" : 15.0,\n" +
                "  \"listProductId\" : [ \"456\" ]\n" +
                "}"))
                .andExpect(status().isCreated)
                .andReturn()

        assertEquals("{\n" +
                "  \"id\" : \"123\",\n" +
                "  \"name\" : \"789\",\n" +
                "  \"reduceAmount\" : 15.0,\n" +
                "  \"listProductId\" : [ \"456\" ]\n" +
                "}", result.response.contentAsString)

        verify { promotionService.insert(any()) }
    }

    @Test
    fun `it updates`() {
        val promotion = Promotion(
                id = "123",
                name = "789",
                reduceAmount = 15f,
                listProductId = mutableListOf("456")
        )

        every { promotionService.update(any()) } returns promotion

        val result = mockMvc.perform(put("/promotion").contentType(MediaType.APPLICATION_JSON_UTF8).content("{\n" +
                "  \"id\" : \"123\",\n" +
                "  \"name\" : \"789\",\n" +
                "  \"reduceAmount\" : 15.0,\n" +
                "  \"listProductId\" : [ \"456\" ]\n" +
                "}"))
                .andExpect(status().isOk)
                .andReturn()

        assertEquals("{\n" +
                "  \"id\" : \"123\",\n" +
                "  \"name\" : \"789\",\n" +
                "  \"reduceAmount\" : 15.0,\n" +
                "  \"listProductId\" : [ \"456\" ]\n" +
                "}", result.response.contentAsString)

        verify { promotionService.update(any()) }
    }

    @Test
    fun `it deletes by id`() {
        val promotion = Promotion(
                id = "123",
                name = "789",
                reduceAmount = 15f,
                listProductId = mutableListOf("456")
        )

        every { promotionService.deleteById("123") } returns Optional.of(promotion)

        val result = mockMvc.perform(delete("/promotion/123").contentType(MediaType.APPLICATION_JSON_UTF8).content("{\n" +
                "  \"id\" : \"123\",\n" +
                "  \"name\" : \"789\",\n" +
                "  \"reduceAmount\" : 15.0,\n" +
                "  \"listProductId\" : [ \"456\" ]\n" +
                "}"))
                .andExpect(status().isAccepted)
                .andReturn()

        assertEquals("", result.response.contentAsString)
        verify { promotionService.deleteById("123") }
    }
}