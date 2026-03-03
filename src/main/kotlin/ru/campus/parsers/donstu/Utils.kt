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
import org.apache.logging.log4j.Logger
import java.security.MessageDigest

@PublishedApi
internal val JsonConfig = Json {
    ignoreUnknownKeys = true
    encodeDefaults = true
    coerceInputValues = true
    isLenient = true
}

suspend inline fun <reified T> HttpResponse.getData(logger: Logger): T {
    val bodyText = this.bodyAsText()
    try {
        return JsonConfig.decodeFromString<DonstuCommonModel<T>>(bodyText).data
    } catch (e: Exception) {
        logger.info("Body was: {}", bodyText)
        throw e
    }
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

private val md = MessageDigest.getInstance("MD5")

fun getMd5Hash(s: String): String {
    return md.digest(s.toByteArray()).toString(Charsets.UTF_8)
}