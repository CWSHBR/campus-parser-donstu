/*
 * Copyright 2026 LLC Campus.
 */

package ru.campus.parsers.donstu.model.schedule

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Расписание.
 *
 * @param isCyclicalSchedule признак циклического расписания
 * @param lessons список занятий
 * @param info служебная информация по запросу расписания
 */
@Serializable
data class DonstuSchedule(
    @SerialName("isCyclicalSchedule")
    val isCyclicalSchedule: Boolean,

    @SerialName("rasp")
    val lessons: List<DonstuLesson>,

    @SerialName("info")
    val info: DonstuScheduleInfo
)
