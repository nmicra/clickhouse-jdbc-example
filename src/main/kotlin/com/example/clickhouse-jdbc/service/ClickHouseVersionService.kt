package com.example.`clickhouse-jdbc`.service

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service

@Service
class ClickHouseVersionService(private val jdbcTemplate: JdbcTemplate) {

    /**
     * Retrieves the ClickHouse server version.
     *
     * @return A string representing the ClickHouse version.
     * @throws RuntimeException if the version cannot be retrieved.
     */
    fun getClickHouseVersion(): String {
        return try {
            jdbcTemplate.queryForObject("SELECT version()", String::class.java) ?: "Unknown"
        } catch (ex: Exception) {
            // Log the exception (you can use a logger instead of printStackTrace in production)
            ex.printStackTrace()
            throw RuntimeException("Failed to retrieve ClickHouse version", ex)
        }
    }
}
