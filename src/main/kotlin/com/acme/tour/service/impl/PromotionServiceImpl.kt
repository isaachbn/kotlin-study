package com.acme.tour.service.impl

import com.acme.tour.exception.PromotionNotFoundException
import com.acme.tour.model.Promotion
import com.acme.tour.repository.PromotionRepository
import com.acme.tour.service.PromotionService
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class PromotionServiceImpl(private val promotionRepository: PromotionRepository): PromotionService {
    override fun getAll(start: Int, size: Int): List<Promotion> =
            this.promotionRepository.findAll(PageRequest.of(start, size)).toList()

    @Cacheable(cacheManager = "cachemanager", cacheNames = ["promotions_find_by_locale"])
    override fun findAllByLocaleContains(locale: String, start: Int, size: Int): List<Promotion> =
            this.promotionRepository.findAllByLocaleContains(locale, PageRequest.of(start, size)).toList()

    override fun findById(id: Long) = this.promotionRepository.findById(id).orElse(null)

    override fun create(promotion: Promotion) {
        this.promotionRepository.save(promotion)
    }

    override fun delete(id: Long) {
        this.promotionRepository.deleteById(id)
    }

    override fun update(id: Long, promotion: Promotion) {
        val promotionModel = this.findById(id) ?: throw PromotionNotFoundException("Identifier $id not exists")

        val promotionUpdate = promotionModel.copy(
                description = promotion.description,
                locale = promotion.locale,
                allInclusive = promotion.allInclusive,
                countDays = promotion.countDays,
                price = promotion.price
        )

        this.create(promotionUpdate)
    }

    override fun count(): Long = this.promotionRepository.count()
}