// src/main/kotlin/com/example/demo/model/PayloadData.kt
package com.example.`clickhouse-jdbc`.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime

@JsonIgnoreProperties(ignoreUnknown = true)
data class PayloadData(
    @JsonProperty("subscriberId") val subscriberId: String? = null,
    @JsonProperty("iccid") val iccid: String? = null,
    @JsonProperty("lastActiveImsi") val lastActiveImsi: String? = null,
    @JsonProperty("imei") val imei: String? = null,
    @JsonProperty("imeisv") val imeisv: String? = null,
    @JsonProperty("lastActiveMsisdn") val lastActiveMsisdn: String? = null,
    @JsonProperty("ip") val ip: String? = null,
    @JsonProperty("isDataSessionOpen") val isDataSessionOpen: Boolean? = null,
    @JsonProperty("dataSessionOpenTime") val dataSessionOpenTime: LocalDateTime? = null,
    @JsonProperty("dataSessionClosureTime") val dataSessionClosureTime: LocalDateTime? = null,
    @JsonProperty("operatorIdentifier") val operatorIdentifier: String? = null,
    @JsonProperty("radioAccessType") val radioAccessType: String? = null,
    @JsonProperty("operatorName") val operatorName: String? = null,
    @JsonProperty("dataRadioAccessType") val dataRadioAccessType: String? = null,
    @JsonProperty("mcc") val mcc: String? = null,
    @JsonProperty("mnc") val mnc: String? = null,
    @JsonProperty("tac") val tac: String? = null,
    @JsonProperty("lac") val lac: String? = null,
    @JsonProperty("cid") val cid: String? = null,
    @JsonProperty("sac") val sac: String? = null,
    @JsonProperty("rac") val rac: String? = null,
    @JsonProperty("enb") val enb: String? = null
)
