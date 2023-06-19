/*
 * Created by Faisal Khan on (c) 16/8/2021.
 */
package com.mietrix.restu_digital_quran.components.tafsir

import com.mietrix.restu_digital_quran.api.models.tafsir.TafsirInfoModel

class TafsirGroupModel(
    val langCode: String,
) {
    var langName = ""
    var tafsirs: List<TafsirInfoModel> = ArrayList()
    var isExpanded = false
}
