package com.example.`clickhouse-jdbc`.repository

import com.example.`clickhouse-jdbc`.model.UpdateCommand
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository
import java.sql.ResultSet

@Repository
class UpdateCommandRepository(private val jdbcTemplate: JdbcTemplate) {

    private val rowMapper = RowMapper<UpdateCommand> { rs: ResultSet, _: Int ->
        UpdateCommand(
            offset = rs.getLong("offset"),
            partition = rs.getByte("partition"),
            timestamp = rs.getTimestamp("timestamp").toLocalDateTime(),
            topic = rs.getString("topic"),
            key = rs.getString("key"),
            updateType = rs.getString("update_type"),
            subscriberId = rs.getString("subscriber_id"),
            iccid = rs.getString("iccid"),
            payload = rs.getString("payload")
        )
    }

    /**
     * Retrieves all UpdateCommand records for a given subscriber_id, sorted by timestamp.
     *
     * @param subscriberId The subscriber ID to filter records.
     * @return A list of UpdateCommand records.
     */
    fun findBySubscriberIdOrderByTimestamp(subscriberId: String): List<UpdateCommand> {
        val sql = """
            SELECT offset, partition, timestamp, topic, key, update_type, subscriber_id, iccid, payload
            FROM kmr.update_command
            WHERE update_type='SIM_STATE' AND timestamp>'2024-09-23 00:00:00.000000' AND subscriber_id = ?
            ORDER BY timestamp ASC
        """.trimIndent()

        return jdbcTemplate.query(sql, rowMapper, subscriberId)
    }
}
