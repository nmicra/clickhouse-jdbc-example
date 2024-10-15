// src/main/kotlin/com/example/demo/util/JsonUtil.kt
package com.example.`clickhouse-jdbc`.util

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue

object JsonUtil {

    val objectMapper: ObjectMapper = ObjectMapper()
        .registerModule(
            KotlinModule.Builder().build() // Correct instantiation of KotlinModule
        )
        .findAndRegisterModules()

    inline fun <reified T> fromJson(json: String): T {
        return objectMapper.readValue(json)
    }

    fun toJson(value: Any): String {
        return objectMapper.writeValueAsString(value)
    }
}
