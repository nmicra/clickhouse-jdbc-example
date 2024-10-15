// src/main/kotlin/com/example/demo/model/ChangeReport.kt
package com.example.`clickhouse-jdbc`.model

data class ChangeReport(
    val subscriberId: String,
    val totalChanges: Int,
    val totalAdditions: Int,
    val fieldChangeCounts: Map<String, Int>,
    val fieldAdditionCounts: Map<String, Int>
)
