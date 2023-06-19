package com.mietrix.restu_digital_quran.api.models.tafsir

import kotlinx.serialization.Serializable

@Serializable
data class AvailableTafsirsModel(
    val tafsirs: Map<String, List<TafsirInfoModel>>
)
