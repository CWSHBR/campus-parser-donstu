/*
 * Copyright 2026 LLC Campus.
 */

package ru.campus.parsers.donstu.model.group

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.campus.parser.sdk.model.Entity
import ru.campus.parsers.donstu.WEBAPP_BASE_URL

/**
 * Модель для получения информации о группе.
 * @param groupName - название группы
 * @param groupId - id группы
 * @param course - курс обучения
 * @param facultyName - название факультета
 * @param academicYearName - название учебного года
 * @param facultyId - id факультета
 */

@Serializable
data class DonstuGroup(
    @SerialName("name")
    val groupName: String,

    @SerialName("id")
    val groupId: Int,

    @SerialName("kurs")
    val course: Int,

    @SerialName("facul")
    val facultyName: String,

    @SerialName("yearName")
    val academicYearName: String,

    @SerialName("facultyID")
    val facultyId: Int
){
    fun toCampusEntity() = Entity(
        type = Entity.Type.Group,
        name = groupName,
        code = groupId.toString(),
        scheduleUrl = "$WEBAPP_BASE_URL/Rasp/Group/$groupId",
        extra = Entity.Extra(
            course = course,
            faculty = facultyName
        )
    )
}

/**
 * Список групп.
 */
typealias DonstuGroups = List<DonstuGroup>