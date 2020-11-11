package com.acme.tour.controller

import com.acme.tour.model.PayLoadError
import com.acme.tour.model.Promotion
import com.acme.tour.service.PromotionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/promotions")
class PromotionController {
    @Autowired
    lateinit var promotionService: PromotionService

    @GetMapping("/{id}")
    fun get(@PathVariable id: Long): ResponseEntity<Any> {
        val promotion = this.promotionService.findById(id)

        if (null == promotion) {
            return ResponseEntity(
                    PayLoadError("Promotion Not Found", "Promotion $id not exists!"),
                    HttpStatus.NOT_FOUND
            )
        }

        return ResponseEntity(promotion, HttpStatus.OK)
    }

    @GetMapping
    fun getAll(
            @RequestParam(required = false, defaultValue = "") locale: String,
            @RequestParam(required = false, defaultValue = "0") start: Int,
            @RequestParam(required = false, defaultValue = "5") size: Int
    ): ResponseEntity<List<Promotion>> {
        val list = this.promotionService.findAllByLocaleContains(locale, start, size)

        return ResponseEntity(list, HttpStatus.OK)
    }

    @PostMapping
    fun create(@RequestBody promotion: Promotion): ResponseEntity<Unit> {
        this.promotionService.create(promotion)

        return ResponseEntity(Unit, HttpStatus.CREATED)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Unit> {
        if (null == this.promotionService.findById(id)) {
            return ResponseEntity(Unit, HttpStatus.NOT_FOUND)
        }

        this.promotionService.delete(id)

        return ResponseEntity(Unit, HttpStatus.ACCEPTED)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody promotion: Promotion): ResponseEntity<Unit> {
        if (null == this.promotionService.findById(id)) {
            return ResponseEntity(Unit, HttpStatus.NOT_FOUND)
        }

        this.promotionService.update(id, promotion)

        return ResponseEntity(Unit, HttpStatus.ACCEPTED)
    }

    @GetMapping("/count")
    fun count() = ResponseEntity.ok().body(mapOf("count" to this.promotionService.count()))
}