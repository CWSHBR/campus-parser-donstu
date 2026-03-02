/*
 * Copyright 2026 LLC Campus.
 */

package ru.campus.parsers.donstu.model.schedule

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Информация об аудитории из блока info.
 *
 * @param name аудитория/строка фильтра
 */
@Serializable
data class DonstuScheduleRoomInfo(
    @SerialName("name")
    val name: String
)

