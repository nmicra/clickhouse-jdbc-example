package com.example.`clickhouse-jdbc`.model

import java.time.LocalDateTime

data class UpdateCommand(
    val offset: Long,
    val partition: Byte,
    val timestamp: LocalDateTime,
    val topic: String,
    val key: String?,
    val updateType: String?,
    val subscriberId: String?,
    val iccid: String?,
    val payload: String?
)