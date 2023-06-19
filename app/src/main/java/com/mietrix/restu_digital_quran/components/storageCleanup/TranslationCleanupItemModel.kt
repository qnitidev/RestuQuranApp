/*
 * Created by Faisal Khan on (c) 16/8/2021.
 */
package com.mietrix.restu_digital_quran.components.storageCleanup

import com.mietrix.restu_digital_quran.components.quran.subcomponents.QuranTranslBookInfo
import com.mietrix.restu_digital_quran.components.transls.TranslBaseModel

class TranslationCleanupItemModel(val bookInfo: QuranTranslBookInfo) : TranslBaseModel() {
    var isDeleted: Boolean = false
}
