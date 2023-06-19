package com.mietrix.restu_digital_quran.activities.reference

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.mietrix.restu_digital_quran.R
import com.mietrix.restu_digital_quran.adapters.reference.ADPSolutionVerses
import com.mietrix.restu_digital_quran.components.quran.ExclusiveVerse
import com.mietrix.restu_digital_quran.components.quran.QuranMeta
import com.mietrix.restu_digital_quran.components.quran.SituationVerse
import com.mietrix.restu_digital_quran.databinding.ActivityExclusiveVersesBinding

class ActivitySolutionVerses : ActivityExclusiveVersesBase() {
    override fun onQuranMetaReady(
        activityView: View,
        intent: Intent,
        savedInstanceState: Bundle?,
        quranMeta: QuranMeta
    ) {
        SituationVerse.prepareInstance(this, quranMeta) { references ->
            initContent(ActivityExclusiveVersesBinding.bind(activityView), references, R.string.titleSolutionVerses)
        }
    }

    override fun getAdapter(
        context: Context,
        width: Int,
        exclusiveVerses: List<ExclusiveVerse>
    ): RecyclerView.Adapter<*> {
        return ADPSolutionVerses(context, width, exclusiveVerses)
    }
}