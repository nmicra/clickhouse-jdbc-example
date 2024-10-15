package com.example.`clickhouse-jdbc`.config

import com.clickhouse.jdbc.ClickHouseDataSource
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.JdbcTemplate
import javax.sql.DataSource

@Configuration
class ClickHouseConfig(
    @Value("\${clickhouse.url}") private val url: String,
    @Value("\${clickhouse.user}") private val user: String,
    @Value("\${clickhouse.password}") private val password: String
) {

    @Bean
    fun clickHouseDataSource(): DataSource {
        val properties = java.util.Properties()
        properties.setProperty("user", user)
        properties.setProperty("password", password)
        return ClickHouseDataSource(url, properties)
    }

    @Bean
    fun clickHouseJdbcTemplate(dataSource: DataSource): JdbcTemplate {
        return JdbcTemplate(dataSource)
    }
}
