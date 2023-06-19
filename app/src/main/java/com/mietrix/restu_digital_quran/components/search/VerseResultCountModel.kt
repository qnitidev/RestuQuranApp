package com.mietrix.restu_digital_quran.components.search

import com.mietrix.restu_digital_quran.components.quran.subcomponents.QuranTranslBookInfo

class VerseResultCountModel(val bookInfo: QuranTranslBookInfo?) : SearchResultModelBase() {
    @JvmField
    var resultCount = 0
}
