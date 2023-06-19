package com.mietrix.restu_digital_quran.views.reader.juzSpinner

import com.mietrix.restu_digital_quran.utils.univ.StringUtils
import com.mietrix.restu_digital_quran.views.reader.spinner.ReaderSpinnerItem

class JuzSpinnerItem(label: CharSequence) : ReaderSpinnerItem() {
    var juzNumber = -1
    var nameArabic = ""
    var nameTransliterated = ""
        set(nameTransliterated) {
            field = nameTransliterated
            searchKeyword = StringUtils.stripDiacritics(nameTransliterated)
        }

    var searchKeyword: String? = null
        private set

    init {
        super.label = label
    }
}
