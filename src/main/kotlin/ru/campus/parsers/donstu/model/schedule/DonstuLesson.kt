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

    @SerialName("датаНачала")
    val startDateTime: String,

    @SerialName("датаОкончания")
    val endDateTime: String,

    @SerialName("перерыв")
    val breakInfo: String?,

    @SerialName("начало")
    val startTime: String,

    @SerialName("конец")
    val endTime: String,

    @SerialName("деньНедели")
    val weekdayIndex: Int,

    @SerialName("день_недели")
    val weekdayName: String,

    @SerialName("код_Семестра")
    val semesterCode: Int,

    @SerialName("типНедели")
    val weekType: Int,

    @SerialName("номерПодгруппы")
    val subgroupNumber: Int,

    @SerialName("дисциплина")
    val discipline: String,

    @SerialName("преподаватель")
    val teacher: String,

    @SerialName("должность")
    val teacherPosition: String,

    @SerialName("аудитория")
    val classroom: String,

    @SerialName("учебныйГод")
    val academicYear: String,

    @SerialName("группа")
    val groupName: String,

    @SerialName("custom1")
    val custom1: String,

    @SerialName("часы")
    val timesLabel: String,

    @SerialName("неделяНачала")
    val startWeek: Int,

    @SerialName("неделяОкончания")
    val endWeek: Int,

    @SerialName("кодПреподавателя")
    val teacherId: Int,

    @SerialName("кодГруппы")
    val groupId: Int,

    @SerialName("фиоПреподавателя")
    val teacherShortName: String,

    @SerialName("кодПользователя")
    val userId: Int,

    @SerialName("элементЦиклРасписания")
    val isCycleElement: Boolean,

    @SerialName("элементГрафика")
    val isGraphElement: Boolean,

    @SerialName("тема")
    val topic: String?,

    @SerialName("номерЗанятия")
    val lessonNumber: Int,

    @SerialName("ссылка")
    val link: String?,

    @SerialName("созданиеВебинара")
    val webinarCreation: Boolean,

    @SerialName("кодВебинара")
    val webinarId: Int?,

    )

