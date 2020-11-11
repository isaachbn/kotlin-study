package com.acme.tour.service

import com.acme.tour.model.Promotion

interface PromotionService {
    fun getAll(start: Int, size: Int): List<Promotion>
    fun findAllByLocaleContains(locale: String, start: Int, size: Int): List<Promotion>
    fun findById(id: Long): Promotion?
    fun create(promotion: Promotion)
    fun delete(id: Long)
    fun update(id: Long, promotion: Promotion)
    fun count(): Long
}