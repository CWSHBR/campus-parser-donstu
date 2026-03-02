/*
 * Copyright 2022 LLC Campus.
 */

package ru.campus.parsers.donstu.group

import io.ktor.client.*
import io.ktor.client.request.*
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.isoDayNumber
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import org.apache.logging.log4j.Logger
import ru.campus.parser.sdk.DateProvider
import ru.campus.parser.sdk.base.ScheduleCollector
import ru.campus.parser.sdk.model.Entity
import ru.campus.parser.sdk.model.TimeTableInterval
import ru.campus.parser.sdk.model.WeekScheduleItem
import ru.campus.parsers.donstu.API_BASE_URL
import ru.campus.parsers.donstu.getData
import ru.campus.parsers.donstu.model.schedule.DonstuAvailableDates

class DonstuGroupScheduleCollector(
    private val httpClient: HttpClient,
    private val logger: Logger,
    private val dateProvider: DateProvider,
) : ScheduleCollector {

    private fun String.toLocalDate(): LocalDate? = // format: "YYYY-MM-DD"
        runCatching {
            LocalDate.parse(this)
        }.getOrNull()

    private fun LocalDate.getWeeksMonday(): LocalDate =
        this.minus(((this.dayOfWeek.isoDayNumber + 6) % 7), DateTimeUnit.DAY)

    suspend fun getMaxAvailableDate(groupId: Int): LocalDate {
        val availableDates: DonstuAvailableDates =
            httpClient.get("$API_BASE_URL/GetRaspDates"){
                parameter("idGroup", groupId)
            }.getData()
        return availableDates.maxDate?.toLocalDate() ?: error("No max date found for group $groupId")
    }


    // Прокидываем дату понедельника недели даты из dateProvider
    override suspend fun collectSchedule(
        entity: Entity,
        intervals: List<TimeTableInterval>,
    ): ScheduleCollector.Result {
        if (entity.type != Entity.Type.Group) error("${entity.type} is not supported. $entity")
        if (entity.code?.toIntOrNull() == null) error("GroupId in code field is invalid. $entity")

        val maxDate = getMaxAvailableDate(entity.code!!.toInt())

        val weekScheduleItems: MutableList<WeekScheduleItem> = mutableListOf()
        var date = dateProvider.getCurrentDateTime().date.getWeeksMonday()

        for (i in 0 until 4) {

            date = date.plus(7, DateTimeUnit.DAY)
            if (date > maxDate) break
        }

        return ScheduleCollector.Result(
            processedEntity = entity,
            weekScheduleItems = weekScheduleItems
        )
    }
}
