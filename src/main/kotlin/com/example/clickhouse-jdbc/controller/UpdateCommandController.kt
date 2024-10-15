// src/main/kotlin/com/example/demo/controller/UpdateCommandController.kt
package com.example.`clickhouse-jdbc`.controller

import com.example.`clickhouse-jdbc`.model.ChangeReport
import com.example.`clickhouse-jdbc`.service.UpdateCommandService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/update-command")
class UpdateCommandController(private val updateCommandService: UpdateCommandService) {

    private val logger = LoggerFactory.getLogger(UpdateCommandController::class.java)

    /**
     * Endpoint to retrieve change report for a given subscriber_id.
     *
     * @param subscriberId The subscriber ID to analyze.
     * @return A ResponseEntity containing the ChangeReport.
     */
    @GetMapping("/changes/{subscriberId}")
    fun getChangeReport(@PathVariable subscriberId: String): ResponseEntity<ChangeReport> {
        return try {
            logger.info("Received request to analyze changes for subscriber_id: $subscriberId")
            val report = updateCommandService.analyzeChanges(subscriberId)
            ResponseEntity.ok(report)
        } catch (ex: Exception) {
            logger.error("Error processing change report for subscriber_id: $subscriberId", ex)
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
        }
    }
}
