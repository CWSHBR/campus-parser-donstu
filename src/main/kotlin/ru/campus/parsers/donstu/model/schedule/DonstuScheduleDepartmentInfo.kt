/*
 * Copyright 2026 LLC Campus.
 */

package ru.campus.parsers.donstu.model.schedule

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Информация о кафедре из блока info.
 *
 * @param name название кафедры/строка фильтра
 */
@Serializable
data class DonstuScheduleDepartmentInfo(
    @SerialName("name")
    val name: String
)

