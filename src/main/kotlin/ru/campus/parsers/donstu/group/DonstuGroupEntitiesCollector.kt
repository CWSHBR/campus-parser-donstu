/*
 * Copyright 2022 LLC Campus.
 */

package ru.campus.parsers.donstu.group

import io.ktor.client.*
import io.ktor.client.request.*
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
        val years: DonstuYearsGroups = httpClient.get("$API_BASE_URL/Rasp/ListYears").getData()
        return years.years.lastOrNull() ?: error("No years found.")
    }

    suspend fun getDonstuGroups(year: String): DonstuGroups {
        return httpClient.get("$API_BASE_URL/raspGrouplist") {
            parameter("year", year)
        }.getData()
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
