package com.mietrix.restu_digital_quran.utils.quran.parser

import android.content.Context
import android.os.Handler
import android.os.Looper
import com.mietrix.restu_digital_quran.components.quran.QuranMeta
import com.mietrix.restu_digital_quran.components.quran.ExclusiveVerse
import com.mietrix.restu_digital_quran.utils.Log
import java.util.concurrent.atomic.AtomicReference

object SituationVersesParser : ExclusiveVersesParser() {
    fun parseVerses(
        context: Context,
        quranMeta: QuranMeta,
        situationVersesRef: AtomicReference<List<ExclusiveVerse>>,
        postRunnable: Runnable
    ) {
        Thread {
            try {
                situationVersesRef.set(
                    parseFromAssets(
                        context,
                        quranMeta,
                        "type0"
                    )
                )
            } catch (e: Exception) {
                Log.saveError(e, "SituationVersesParser.parseVerses")
            }

            Handler(Looper.getMainLooper()).post(postRunnable)
        }.start()
    }
}
