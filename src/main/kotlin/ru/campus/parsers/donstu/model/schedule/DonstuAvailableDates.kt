/*
 * Copyright 2026 LLC Campus.
 */

package ru.campus.parsers.donstu.model.schedule

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Доступные даты.
 *
 * @param minDate минимальная доступная дата (YYYY-MM-DD)
 * @param maxDate максимальная доступная дата (YYYY-MM-DD)
 * @param selectedDate выбранная дата (YYYY-MM-DD)
 * @param dates список дат с наличием lesson в этот день (YYYY-MM-DD)
 */
@Serializable
data class DonstuAvailableDates(
    @SerialName("minDate")
    val minDate: String,

    @SerialName("maxDate")
    val maxDate: String,

    @SerialName("selDate")
    val selectedDate: String,

    @SerialName("dates")
    val dates: List<String>
)