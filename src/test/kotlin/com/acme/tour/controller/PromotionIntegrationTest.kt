package com.acme.tour.controller

import com.acme.tour.model.Promotion
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.*
import java.net.URI

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PromotionIntegrationTest {
    @Autowired
    lateinit var testRestTemplate: TestRestTemplate

    @Test
    fun testCreateNewPromotionSuccess() {
        val promotion = Promotion(
                allInclusive = false,
                countDays = 10,
                description = "NewPromotion",
                locale = "Brazil",
                price = 10000.00
        )
        val result = testRestTemplate.postForEntity("/promotions", promotion, promotion.javaClass)
        Assertions.assertEquals(result.statusCode, HttpStatus.CREATED)
        Assertions.assertNotNull(result.body?.id)
    }

    @Test
    fun testFindPromotionSuccess() {
        val result = testRestTemplate.getForEntity("/promotions/1", Promotion::class.java)
        Assertions.assertEquals(result.statusCode, HttpStatus.OK)
        Assertions.assertNotNull(result.body?.id)
    }

    @Test
    fun testFinAllPromotionSuccess() {
        val result = testRestTemplate.getForEntity("/promotions", List::class.java)
        Assertions.assertEquals(result.statusCode, HttpStatus.OK)
        Assertions.assertNotEquals(0, result.body?.size)
    }

    @Test
    fun testUpdatePromotionSuccess() {
        val update = "Update"
        val promotion = Promotion(
                allInclusive = false,
                countDays = 10,
                description = update,
                locale = "Brazil",
                price = 10000.00
        )
        val uri = URI("/promotions/1")
        val requestEntity = RequestEntity(promotion, HttpMethod.PUT, uri)
        val result = testRestTemplate.exchange(requestEntity, Unit::class.java)
        Assertions.assertEquals(result.statusCode, HttpStatus.ACCEPTED)
    }

    @Test
    fun testDeletePromotionSuccess() {
        val uri = URI("/promotions/3")
        val requestEntity = RequestEntity(Unit, HttpMethod.DELETE, uri)
        val result = testRestTemplate.exchange(requestEntity, Unit::class.java)
        Assertions.assertEquals(result.statusCode, HttpStatus.ACCEPTED)
    }
}