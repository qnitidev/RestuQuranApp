package com.mietrix.restu_digital_quran.views.homepage2

import android.content.Context
import android.content.Intent
import android.util.AttributeSet
import com.peacedesign.android.utils.Dimen
import com.mietrix.restu_digital_quran.R
import com.mietrix.restu_digital_quran.activities.reference.ActivityDua
import com.mietrix.restu_digital_quran.adapters.reference.ADPDua
import com.mietrix.restu_digital_quran.components.quran.ExclusiveVerse
import com.mietrix.restu_digital_quran.components.quran.QuranDua
import com.mietrix.restu_digital_quran.components.quran.QuranMeta
import com.mietrix.restu_digital_quran.databinding.LytHomepageTitledItemTitleBinding
import com.mietrix.restu_digital_quran.utils.extensions.color

class FeaturedDuaLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : HomepageCollectionLayoutBase(context, attrs, defStyleAttr) {
    override fun getHeaderTitle(): Int {
        return R.string.strTitleFeaturedDuas
    }

    override fun getHeaderIcon(): Int {
        return R.drawable.dr_icon_rabbana
    }

    override fun setupHeader(header: LytHomepageTitledItemTitleBinding) {
        header.titleIcon.setColorFilter(context.color(R.color.colorPrimary))
    }

    override fun onViewAllClick() {
        context.startActivity(Intent(context, ActivityDua::class.java))
    }

    private fun refreshFeatured(ctx: Context, duas: List<ExclusiveVerse>) {
        hideLoader()

        val featured = duas.subList(0, duas.size.coerceAtMost(6))
        resolveListView().adapter = ADPDua(ctx, Dimen.dp2px(ctx, 200f), featured)
    }

    override  fun refresh(quranMeta: QuranMeta) {
        showLoader()

        QuranDua.prepareInstance(context, quranMeta) { duas ->
            refreshFeatured(context, duas)
        }
    }
}