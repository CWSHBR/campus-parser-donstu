/*
 * Copyright 2026 LLC Campus.
 */

package ru.campus.parsers.donstu.model.schedule

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Тип недели (из блока info).
 *
 * @param typeWeekId идентификатор типа недели
 * @param name название типа недели
 * @param shortName короткое название
 */
@Serializable
data class DonstuWeekType(
    @SerialName("typeWeekID")
    val typeWeekId: Int,

    @SerialName("name")
    val name: String,

    @SerialName("shortName")
    val shortName: String
)

