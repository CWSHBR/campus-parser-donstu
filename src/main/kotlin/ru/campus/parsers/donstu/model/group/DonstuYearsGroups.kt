/*
 * Copyright 2026 LLC Campus.
 */

package ru.campus.parsers.donstu.model.group

import kotlinx.serialization.Serializable

/**
 * Модель для получения годов обучения (необходимо для дальнейшего получения групп).
 * @param years - список годов обучения (например "2022-2023", "2023-2024")
 */

@Serializable
data class DonstuYearsGroups(
    val years: List<String>
)