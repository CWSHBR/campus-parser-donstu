/*
 * Copyright 2026 LLC Campus.
 */

package ru.campus.parsers.donstu

import io.ktor.client.statement.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import ru.campus.parsers.donstu.model.DonstuCommonModel

private val JsonConfig = Json {
    ignoreUnknownKeys = true
    encodeDefaults = true
}

suspend fun <T> HttpResponse.getData(): T {
    return JsonConfig.decodeFromString<DonstuCommonModel<T>>(this.bodyAsText()).data
}