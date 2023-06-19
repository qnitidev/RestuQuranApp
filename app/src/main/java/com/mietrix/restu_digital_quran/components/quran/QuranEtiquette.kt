package com.mietrix.restu_digital_quran.components.quran

import android.content.Context
import com.mietrix.restu_digital_quran.utils.quran.parser.QuranEtiquetteParser
import java.util.concurrent.atomic.AtomicReference

object QuranEtiquette {
    private val sQuranEtiquetteRef = AtomicReference<List<ExclusiveVerse>>()
    fun prepareInstance(
        context: Context,
        quranMeta: QuranMeta,
        callback: (List<ExclusiveVerse>) -> Unit
    ) {
        if (sQuranEtiquetteRef.get() == null) {
            prepare(context, quranMeta, callback)
        } else {
            callback(sQuranEtiquetteRef.get())
        }
    }

    private fun prepare(
        context: Context,
        quranMeta: QuranMeta,
        callback: (List<ExclusiveVerse>) -> Unit
    ) {
        QuranEtiquetteParser.parseVerses(
            context,
            quranMeta,
            sQuranEtiquetteRef
        ) { callback(sQuranEtiquetteRef.get()) }
    }
}
