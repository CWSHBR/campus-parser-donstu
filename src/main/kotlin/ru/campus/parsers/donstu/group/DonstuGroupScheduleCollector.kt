/*
 * Copyright 2022 LLC Campus.
 */

package ru.campus.parsers.donstu.group

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.plus
import org.apache.logging.log4j.Logger
import ru.campus.parser.sdk.DateProvider
import ru.campus.parser.sdk.base.ScheduleCollector
import ru.campus.parser.sdk.model.*
import ru.campus.parsers.donstu.API_BASE_URL
import ru.campus.parsers.donstu.USER_AGENT_VALUE
import ru.campus.parsers.donstu.getData
import ru.campus.parsers.donstu.getMd5Hash
import ru.campus.parsers.donstu.getMondayOfWeek
import ru.campus.parsers.donstu.model.schedule.DonstuAvailableDates
import ru.campus.parsers.donstu.model.schedule.DonstuLesson
import ru.campus.parsers.donstu.model.schedule.DonstuSchedule
import ru.campus.parsers.donstu.toLocalDate

class DonstuGroupScheduleCollector(
    private val httpClient: HttpClient,
    private val logger: Logger,
    private val dateProvider: DateProvider,
) : ScheduleCollector {
    suspend fun getMaxAvailableDate(groupId: Int): LocalDate? {
        val res = httpClient.get("$API_BASE_URL/GetRaspDates"){
            parameter("idGroup", groupId)
            userAgent(USER_AGENT_VALUE)
        }
        if (res.status != HttpStatusCode.OK) error("Max available date could not be retrieved. Status: ${res.status}")

        val availableDates: DonstuAvailableDates = res.getData(logger)
        return availableDates.maxDate?.toLocalDate()
    }

    suspend fun getSchedule(groupId: Int, date: LocalDate): DonstuSchedule {
        val res = httpClient.get("$API_BASE_URL/Rasp") {
            parameter("idGroup", groupId)
            parameter("sdate", date.toString())
            userAgent(USER_AGENT_VALUE)
        }
        if (res.status != HttpStatusCode.OK) error("Schedule could not be retrieved. Status: ${res.status}")
        return res.getData(logger)
    }


    override suspend fun collectSchedule(
        entity: Entity,
        intervals: List<TimeTableInterval>,
    ): ScheduleCollector.Result {
        if (entity.type != Entity.Type.Group) error("${entity.type} is not supported. $entity")
        if (entity.code?.toIntOrNull() == null) error("GroupId in code field is invalid. $entity")
        val groupId = entity.code!!.toInt()

        val maxDate = getMaxAvailableDate(groupId)

        val weekScheduleItems: MutableList<WeekScheduleItem> = mutableListOf()
        // Прокидываем дату понедельника недели даты из dateProvider
        var date = dateProvider.getCurrentDateTime().date.getMondayOfWeek()

        for (i in 0 until 4) {
            if (maxDate == null || date > maxDate) break
            val schedule = getSchedule(groupId, date)
            weekScheduleItems.addAll(
                schedule.lessons.map { lesson ->
                    val lessonLocalDate = lesson.date.toLocalDate() ?:
                    error("Invalid date format in schedule: ${lesson.date}")
                    val dayOfWeek = lessonLocalDate.dayOfWeek
                    WeekScheduleItem(
                        dayOfWeek = dayOfWeek,
                        timeTableInterval = TimeTableInterval(lesson.lessonNumber, lesson.startTime, lesson.endTime),
                        dayCondition = ExplicitDatePredicate(lessonLocalDate),
                        lesson = processLesson(lesson)
                    )
                }
            )
            date = date.plus(7, DateTimeUnit.DAY)
        }

        return ScheduleCollector.Result(
            processedEntity = entity,
            weekScheduleItems = weekScheduleItems
        )
    }

    fun processLesson(lesson: DonstuLesson): Schedule.Lesson {
        val (type, subject) = processDiscipline(lesson.discipline)
        return Schedule.Lesson(
            subject = subject,
            comment = lesson.custom1,
            type = type,
            classroom = if (lesson.classroom.isNullOrBlank() || lesson.classroom.length < 2) null else lesson.classroom,
            teachers = processTeachers(lesson),
            links = processLinks(lesson)
        )
    }

    fun processDiscipline(discipline: String): Pair<String?, String> {
        val strs = discipline.split(" ", limit = 2)
        return if (strs.size == 2) {
             try {
                convertLessonType(strs[0]) to strs[1]
            } catch (_: Exception) {
                null to discipline
            }
        } else {
            null to discipline
        }
    }

    fun convertLessonType(shortType: String): String =
        when (shortType) {
            "пр" -> "Практика"
            "лек" -> "Лекция"
            "зач" -> "Зачет"
            "лаб" -> "Лабораторные"
            "экз" -> "Экзамен"
            "зчО" -> "Зачет с оценкой"
            else -> error("Unknown type $shortType")
        }

    fun processTeachers(lesson: DonstuLesson): List<Schedule.Entity> {
        if (lesson.teacher.length < 2) return emptyList()

        val code = lesson.teacherId?.toString() ?: getMd5Hash(lesson.teacher)

        return listOf(Schedule.Entity(
            name = lesson.teacher,
            code = code
        ))
    }

    fun processLinks(lesson: DonstuLesson): List<Schedule.Link> =
        if (lesson.link != null) {
            listOf(Schedule.Link(title = "Вебинар", url = lesson.link))
        } else {
            emptyList()
        }
}
