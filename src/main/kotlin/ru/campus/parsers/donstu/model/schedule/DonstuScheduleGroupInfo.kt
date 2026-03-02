/*
 * Copyright 2026 LLC Campus.
 */

package ru.campus.parsers.donstu.model.schedule

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Информация о группе из блока info.
 *
 * @param name название группы
 * @param id идентификатор группы
 * @param academicYear учебный год
 */
@Serializable
data class DonstuScheduleGroupInfo(
    @SerialName("name")
    val name: String,

    @SerialName("groupID")
    val id: Int,

    @SerialName("year")
    val academicYear: String
)

