package com.acme.tour.service

import com.acme.tour.exception.PromotionNotFoundException
import com.acme.tour.helper.MockitoHelper
import com.acme.tour.model.Promotion
import com.acme.tour.repository.PromotionRepository
import com.acme.tour.service.impl.PromotionServiceImpl
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageImpl
import java.util.*

@SpringBootTest
class PromotionServiceUnitTest {
    @InjectMocks
    lateinit var service: PromotionServiceImpl

    @Mock
    lateinit var promotionRepository: PromotionRepository

    @BeforeEach
    fun startMock() {
        val promotion = Promotion(
                id = 1, price = 10.00, locale = "test_unit",
                description = "test_unit", countDays = 1, allInclusive = true
        )
        val page = PageImpl(listOf(promotion))
        `when`(promotionRepository.findAllByLocaleContains(anyString(), MockitoHelper.anyObject())).thenReturn(page)
        `when`(promotionRepository.findById(anyLong())).thenReturn(Optional.of(promotion))
        `when`(promotionRepository.save(any(Promotion::class.java))).thenReturn(promotion)
    }

    @DisplayName("Test success find one promotion")
    @Test
    fun testFindSuccessPromotion() {
        Assertions.assertEquals(1, service.findById(1).id)
    }

    @DisplayName("Test fail find object not exist")
    @Test
    fun testFindEmptyPromotion() {
        `when`(promotionRepository.findById(anyLong())).thenReturn(Optional.empty())
        Assertions.assertNull(service.findById(1))
    }

    @DisplayName("Test success all list promotion")
    @Test
    fun testFindAllSuccessPromotions() {
        Assertions.assertEquals(1, service.findAllByLocaleContains("", 0, 1).size)
    }

    @DisplayName("Test create new promotion")
    @Test
    fun testCreateSuccessPromotion() {
        Assertions.assertEquals(Unit, service.create(Promotion()))
    }

    @DisplayName("Test remove success promotion")
    @Test
    fun testDeleteSuccessPromotion() {
        Assertions.assertEquals(Unit, service.delete(1))
    }

    @DisplayName("Test update success promotion")
    @Test
    fun testUpdateSuccessPromotion() {
        Assertions.assertEquals(Unit, service.update(1, Promotion()))
    }

    @DisplayName("Test update fail not found exception")
    @Test
    fun testUpdatePromotionNotFoundException() {
        `when`(promotionRepository.findById(anyLong())).thenReturn(Optional.empty())

        Assertions.assertThrows(PromotionNotFoundException::class.java) {
            service.update(1, Promotion())
        }
    }
}