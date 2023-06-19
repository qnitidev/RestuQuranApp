package com.mietrix.restu_digital_quran.api.models.recitation

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AvailableRecitationTranslationsModel(
    @SerialName("url-info") val urlInfo: RecitationsCommonUrlInfoModel,
    @SerialName("reciters") val reciters: List<RecitationTranslationInfoModel>
)
