/*
 * Copyright 2026 LLC Campus.
 */

package ru.campus.parsers.donstu.model

import kotlinx.serialization.Serializable

/**
 * Общая модель для получения данных. Содержит в себе поле data, в котором находится нужная информация.
 * @param data - данные, которые были запрошены
 * @param state - некий статус
 * @param msg - сообщение о сути запроса
 * @param time - время ответа от сервера
 */

@Serializable
data class DonstuCommonModel<T>(
    val data: T,
    val state: Int,
    val msg: String,
    val time: Double
)