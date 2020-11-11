package com.acme.tour.model

import java.io.Serializable
import javax.persistence.*

@Entity
@Table(name = "promotions")
data class Promotion (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = 0,
        val description: String = "",
        val locale:  String = "",
        val allInclusive: Boolean = false,
        val countDays: Int = 0,
        val price: Double = 0.0
): Serializable