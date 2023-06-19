package com.mietrix.restu_digital_quran.components.quran

import android.content.Context
import com.mietrix.restu_digital_quran.utils.quran.parser.SituationVersesParser
import java.util.concurrent.atomic.AtomicReference

object SituationVerse {
    private val sSituationVerseRef = AtomicReference<List<ExclusiveVerse>>()
    fun prepareInstance(
        context: Context,
        quranMeta: QuranMeta,
        callback: (List<ExclusiveVerse>) -> Unit
    ) {
        if (sSituationVerseRef.get() == null) {
            prepare(context, quranMeta, callback)
        } else {
            callback(sSituationVerseRef.get())
        }
    }

    private fun prepare(
        context: Context,
        quranMeta: QuranMeta,
        callback: (List<ExclusiveVerse>) -> Unit
    ) {
        SituationVersesParser.parseVerses(
            context,
            quranMeta,
            sSituationVerseRef
        ) { callback(sSituationVerseRef.get()) }
    }
}
