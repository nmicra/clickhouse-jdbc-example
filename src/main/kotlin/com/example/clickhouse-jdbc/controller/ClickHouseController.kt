package com.example.`clickhouse-jdbc`.controller

import com.example.`clickhouse-jdbc`.service.ClickHouseVersionService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/clickhouse")
class ClickHouseController(private val versionService: ClickHouseVersionService) {

    /**
     * Endpoint to retrieve the ClickHouse server version.
     *
     * @return A ResponseEntity containing the version information.
     */
    @GetMapping("/version")
    fun getClickHouseVersion(): ResponseEntity<VersionResponse> {
        return try {
            val version = versionService.getClickHouseVersion()
            ResponseEntity.ok(VersionResponse(version))
        } catch (ex: Exception) {
            // Log the exception (you can use a logger instead of printStackTrace in production)
            ex.printStackTrace()
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(VersionResponse("Error retrieving version"))
        }
    }

    /**
     * Data class representing the version response.
     */
    data class VersionResponse(
        val version: String
    )
}
