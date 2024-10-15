// src/main/kotlin/com/example/demo/service/UpdateCommandService.kt
package com.example.`clickhouse-jdbc`.service

import com.example.`clickhouse-jdbc`.model.ChangeReport
import com.example.`clickhouse-jdbc`.model.PayloadData
import com.example.`clickhouse-jdbc`.repository.UpdateCommandRepository
import com.example.`clickhouse-jdbc`.util.JsonUtil
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import kotlin.reflect.full.memberProperties

@Service
class UpdateCommandService(private val repository: UpdateCommandRepository) {

    private val logger = LoggerFactory.getLogger(UpdateCommandService::class.java)

    /**
     * Retrieves and analyzes payloads for a given subscriber_id to identify field changes and additions.
     *
     * @param subscriberId The subscriber ID to analyze.
     * @return A ChangeReport detailing the changes and additions.
     */
    fun analyzeChanges(subscriberId: String): ChangeReport {
        logger.info("Analyzing changes for subscriber_id: $subscriberId")

        val updateCommands = repository.findBySubscriberIdOrderByTimestamp(subscriberId)

        if (updateCommands.isEmpty()) {
            logger.warn("No records found for subscriber_id: $subscriberId")
            return ChangeReport(
                subscriberId = subscriberId,
                totalChanges = 0,
                totalAdditions = 0,
                fieldChangeCounts = emptyMap(),
                fieldAdditionCounts = emptyMap()
            )
        }

        val fieldChangeCounts = mutableMapOf<String, Int>()
        val fieldAdditionCounts = mutableMapOf<String, Int>()
        var totalChanges = 0
        var totalAdditions = 0

        var previousPayload: PayloadData? = null

        for (command in updateCommands) {
            val payloadJson = command.payload
            if (payloadJson.isNullOrBlank()) {
                logger.warn("Empty payload for record with timestamp: ${command.timestamp}")
                continue
            }

            val currentPayload: PayloadData = try {
                JsonUtil.objectMapper.readValue(payloadJson, PayloadData::class.java)
            } catch (ex: Exception) {
                logger.error("Error parsing payload JSON for subscriber_id: $subscriberId at timestamp: ${command.timestamp}", ex)
                continue
            }

            if (previousPayload != null) {
                // Compare currentPayload with previousPayload
                val changes = comparePayloads(previousPayload, currentPayload)

                // Update counts
                changes.changes.forEach { field ->
                    fieldChangeCounts[field] = fieldChangeCounts.getOrDefault(field, 0) + 1
                    totalChanges++
                }

                changes.additions.forEach { field ->
                    fieldAdditionCounts[field] = fieldAdditionCounts.getOrDefault(field, 0) + 1
                    totalAdditions++
                }
            }

            previousPayload = currentPayload
        }

        return ChangeReport(
            subscriberId = subscriberId,
            totalChanges = totalChanges,
            totalAdditions = totalAdditions,
            fieldChangeCounts = fieldChangeCounts,
            fieldAdditionCounts = fieldAdditionCounts
        )
    }

    /**
     * Compares two PayloadData objects and identifies changed and added fields.
     *
     * @param previous The previous payload.
     * @param current The current payload.
     * @return A Pair containing lists of changed and added fields.
     */
    private fun comparePayloads(previous: PayloadData, current: PayloadData): Changes {
        val changes = mutableListOf<String>()
        val additions = mutableListOf<String>()

        val properties = PayloadData::class.memberProperties

        for (prop in properties) {
            val previousValue = prop.get(previous)
            val currentValue = prop.get(current)

            if (previousValue == null && currentValue != null) {
                additions.add(prop.name)
            } else if (previousValue != null && currentValue != null && previousValue != currentValue) {
                changes.add(prop.name)
            }
            // Fields removed (previousValue != null && currentValue == null) are not counted as per requirement
        }

        return Changes(changes, additions)
    }

    /**
     * Data class representing changes and additions between two payloads.
     */
    private data class Changes(
        val changes: List<String>,
        val additions: List<String>
    )
}
