/*
 * Created by Faisal Khan on (c) 16/8/2021.
 */
package com.mietrix.restu_digital_quran.components.storageCleanup

import com.mietrix.restu_digital_quran.api.models.recitation.RecitationInfoModel

data class RecitationCleanupItemModel(
    val recitationModel: RecitationInfoModel,
    val downloadsCount: Int,
    var isCleared: Boolean = false
)
