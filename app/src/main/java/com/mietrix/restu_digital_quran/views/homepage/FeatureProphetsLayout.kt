package com.mietrix.restu_digital_quran.views.homepage2

import android.content.Context
import android.content.Intent
import android.util.AttributeSet
import com.mietrix.restu_digital_quran.R
import com.mietrix.restu_digital_quran.activities.ActivityProphets
import com.mietrix.restu_digital_quran.adapters.ADPProphets
import com.mietrix.restu_digital_quran.components.quran.QuranMeta
import com.mietrix.restu_digital_quran.components.quran.QuranProphet.Companion.prepareInstance
import com.mietrix.restu_digital_quran.utils.extensions.dp2px

class FeatureProphetsLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : HomepageCollectionLayoutBase(context, attrs, defStyleAttr) {
    override fun getHeaderTitle(): Int {
        return R.string.strTitleFeaturedProphets;
    }

    override fun getHeaderIcon(): Int {
        return R.drawable.prophets;
    }

    override fun onViewAllClick() {
        context.startActivity(Intent(context, ActivityProphets::class.java))
    }

    override fun refresh(quranMeta: QuranMeta) {
        showLoader()
        prepareInstance(context, quranMeta) { quranProphet ->
            hideLoader()
            resolveListView().adapter = ADPProphets(context, context.dp2px(300f), 10).apply {
                setProphets(quranProphet.prophets)
            }
        }
    }
}