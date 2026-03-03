/*
 * Copyright 2022 LLC Campus.
 */

package ru.campus.parsers.donstu.group

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.HttpStatusCode
import org.apache.logging.log4j.Logger
import ru.campus.parser.sdk.base.EntitiesCollector
import ru.campus.parser.sdk.model.Entity
import ru.campus.parsers.donstu.API_BASE_URL
import ru.campus.parsers.donstu.getData
import ru.campus.parsers.donstu.model.group.DonstuGroups
import ru.campus.parsers.donstu.model.group.DonstuYearsGroups

class DonstuGroupEntitiesCollector(
    private val httpClient: HttpClient,
    private val logger: Logger,
) : EntitiesCollector {
    suspend fun getEduYears(): String {
        val res = httpClient.get("$API_BASE_URL/Rasp/ListYears")
        if (res.status != HttpStatusCode.OK) error("Years could not be retrieved. Status: ${res.status}")
        val years: DonstuYearsGroups = res.getData()
        return years.years.lastOrNull() ?: error("No years found.")
    }

    suspend fun getDonstuGroups(year: String): DonstuGroups {
        val res = httpClient.get("$API_BASE_URL/raspGrouplist") {
            parameter("year", year)
        }
        if (res.status != HttpStatusCode.OK) error("Groups could not be retrieved. Status: ${res.status}")
        return res.getData()
    }

    override suspend fun collectEntities(): List<Entity> {
        logger.info("Started collecting groups in collectEntities()")
        val years = getEduYears()
        logger.info("Got edu years: $years")
        val groups = getDonstuGroups(years)
        logger.info("Got ${groups.size} groups.")
        return groups.map { it.toCampusEntity() }
    }
}
