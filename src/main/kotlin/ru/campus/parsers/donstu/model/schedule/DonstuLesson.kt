/*
 * Copyright 2026 LLC Campus.
 */

package ru.campus.parsers.donstu.model.schedule

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Элемент расписания (занятие).
 */
@Serializable
data class DonstuLesson(
    @SerialName("код")
    val id: Long,

    @SerialName("дата")
    val date: String,

    @SerialName("перерыв")
    val breakInfo: Double? = null,

    @SerialName("начало")
    val startTime: String,

    @SerialName("конец")
    val endTime: String,

    @SerialName("деньНедели")
    val weekdayIndex: Int,

    @SerialName("типНедели")
    val weekType: Int,

    @SerialName("дисциплина")
    val discipline: String,

    @SerialName("преподаватель")
    val teacher: String = "",

    @SerialName("аудитория")
    val classroom: String,

    @SerialName("группа")
    val groupName: String,

    @SerialName("custom1")
    val custom1: String = "",

    @SerialName("кодПреподавателя")
    val teacherId: Int? = null,

    @SerialName("кодГруппы")
    val groupId: Int,

    @SerialName("фиоПреподавателя")
    val teacherShortName: String = "",

    @SerialName("номерЗанятия")
    val lessonNumber: Int,

    @SerialName("ссылка")
    val link: String?

    )

