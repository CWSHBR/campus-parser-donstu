/*
 * Copyright 2026 LLC Campus.
 */

package ru.campus.parsers.donstu.model.schedule

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Информация о преподавателе из блока info.
 *
 * @param name имя/строка фильтра
 * @param userId идентификатор пользователя (может отсутствовать)
 */
@Serializable
data class DonstuScheduleTeacherInfo(
    @SerialName("name")
    val name: String,

    @SerialName("userID")
    val userId: Int?
)

