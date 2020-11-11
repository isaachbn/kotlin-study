package com.acme.tour.repository

import com.acme.tour.model.Promotion
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface PromotionRepository: PagingAndSortingRepository<Promotion, Long> {
    fun findAllByLocaleContains(locale: String, pageable: Pageable): Page<Promotion>
}