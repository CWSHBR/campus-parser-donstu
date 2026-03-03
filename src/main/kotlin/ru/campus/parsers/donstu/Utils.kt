/*
 * Copyright 2026 LLC Campus.
 */

package ru.campus.parsers.donstu

import io.ktor.client.statement.*
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.isoDayNumber
import kotlinx.datetime.minus
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import ru.campus.parsers.donstu.model.DonstuCommonModel

@PublishedApi
internal val JsonConfig = Json {
    ignoreUnknownKeys = true
    encodeDefaults = true
}

suspend inline fun <reified T> HttpResponse.getData(): T {
    return JsonConfig.decodeFromString<DonstuCommonModel<T>>(this.bodyAsText()).data
}

fun String.toLocalDate(): LocalDate? = // format: "YYYY-MM-DD"
    runCatching {
        LocalDate.parse(this.take(10))
    }.getOrNull()

fun String.toLocalDateTime(): LocalDateTime? = // format: "YYYY-MM-DDTHH:MM:SS"
    runCatching {
        LocalDateTime.parse(this)
    }.getOrNull()

fun LocalDate.getMondayOfWeek(): LocalDate =
    this.minus(((this.dayOfWeek.isoDayNumber + 6) % 7), DateTimeUnit.DAY)