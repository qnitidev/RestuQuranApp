package com.mietrix.restu_digital_quran.views.homepage2

import android.content.Context
import android.content.Intent
import android.util.AttributeSet
import com.peacedesign.android.utils.Dimen
import com.mietrix.restu_digital_quran.R
import com.mietrix.restu_digital_quran.activities.reference.ActivitySolutionVerses
import com.mietrix.restu_digital_quran.adapters.reference.ADPSolutionVerses
import com.mietrix.restu_digital_quran.components.quran.ExclusiveVerse
import com.mietrix.restu_digital_quran.components.quran.QuranMeta
import com.mietrix.restu_digital_quran.components.quran.SituationVerse
import com.mietrix.restu_digital_quran.databinding.LytHomepageTitledItemTitleBinding
import com.mietrix.restu_digital_quran.utils.extensions.color

class QuranSolutionVersesLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : HomepageCollectionLayoutBase(context, attrs, defStyleAttr) {
    override fun getHeaderTitle(): Int {
        return R.string.titleSolutionVerses
    }

    override fun getHeaderIcon(): Int {
        return R.drawable.dr_icon_read_quran
    }

    override fun showViewAllBtn(): Boolean {
        return true
    }

    override fun setupHeader(header: LytHomepageTitledItemTitleBinding) {
        header.titleIcon.setColorFilter(context.color(R.color.warning))
    }

    override fun onViewAllClick() {
        context.startActivity(Intent(context, ActivitySolutionVerses::class.java))
    }

    private fun refreshVerses(ctx: Context, verses: List<ExclusiveVerse>) {
        hideLoader()

        val featured = verses.subList(0, verses.size.coerceAtMost(10))
        resolveListView().adapter = ADPSolutionVerses(ctx, Dimen.dp2px(ctx, 200f), featured)
    }

    override fun refresh(quranMeta: QuranMeta) {
        showLoader()

        SituationVerse.prepareInstance(context, quranMeta) { references ->
            refreshVerses(context, references)
        }
    }
}