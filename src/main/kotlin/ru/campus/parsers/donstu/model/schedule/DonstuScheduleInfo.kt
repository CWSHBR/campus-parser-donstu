/*
 * Copyright 2026 LLC Campus.
 */

package ru.campus.parsers.donstu.model.schedule

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Служебная информация по расписанию.
 */
@Serializable
data class DonstuScheduleInfo(
    @SerialName("group")
    val group: DonstuScheduleGroupInfo,

    @SerialName("prepod")
    val teacher: DonstuScheduleTeacherInfo,

    @SerialName("kafedra")
    val department: DonstuScheduleDepartmentInfo,

    @SerialName("aud")
    val room: DonstuScheduleRoomInfo,

    @SerialName("year")
    val academicYear: String,

    @SerialName("curWeekNumber")
    val currentWeekNumber: Int,

    @SerialName("curNumNed")
    val currentWeekParity: Int,

    @SerialName("selectedNumNed")
    val selectedWeekParity: Int,

    @SerialName("curSem")
    val currentSemester: Int,

    @SerialName("typesWeek")
    val weekTypes: List<DonstuWeekType>,

    @SerialName("fixedInCache")
    val fixedInCache: Boolean,

    @SerialName("date")
    val dateFrom: String,

    @SerialName("lastDate")
    val dateTo: String,

    @SerialName("dateUploadingRasp")
    val scheduleUploadedAt: String
)

