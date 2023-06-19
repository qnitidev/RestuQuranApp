/*
 * Created by Faisal Khan on (c) 16/8/2021.
 */
package com.mietrix.restu_digital_quran.components.storageCleanup

import com.mietrix.restu_digital_quran.api.models.tafsir.TafsirInfoModel

data class TafsirCleanupItemModel(
    val tafsirModel: TafsirInfoModel,
    val downloadsCount: Int,
    var isCleared: Boolean = false
)
