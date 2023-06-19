package com.mietrix.restu_digital_quran.views.homepage2

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.peacedesign.android.utils.Dimen
import com.mietrix.restu_digital_quran.R
import com.mietrix.restu_digital_quran.activities.ActivityReadHistory
import com.mietrix.restu_digital_quran.adapters.ADPReadHistory
import com.mietrix.restu_digital_quran.components.quran.QuranMeta
import com.mietrix.restu_digital_quran.databinding.LytHomepageTitledItemTitleBinding
import com.mietrix.restu_digital_quran.db.readHistory.ReadHistoryDBHelper
import com.mietrix.restu_digital_quran.interfaceUtils.Destroyable
import com.mietrix.restu_digital_quran.utils.extensions.color
import com.mietrix.restu_digital_quran.utils.extensions.dp2px
import com.mietrix.restu_digital_quran.utils.extensions.removeView
import com.mietrix.restu_digital_quran.utils.extensions.setTextColorResource
import com.mietrix.restu_digital_quran.utils.extensions.setTextSizePx
import com.mietrix.restu_digital_quran.utils.extensions.updatePaddings
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ReadHistoryLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : HomepageCollectionLayoutBase(context, attrs, defStyleAttr), Destroyable {
    private var dbHelper: ReadHistoryDBHelper? = null
    private var adapter: ADPReadHistory? = null
    private var firstTime = true
    override fun destroy() {
        dbHelper?.close()
    }

    override fun getHeaderTitle(): Int {
        return R.string.strTitleReadHistory;
    }

    override fun getHeaderIcon(): Int {
        return R.drawable.dr_icon_history;
    }

    override fun initialize() {
        super.initialize()

        dbHelper = ReadHistoryDBHelper(context)
    }

    override fun setupHeader(header: LytHomepageTitledItemTitleBinding) {
        header.titleIcon.setColorFilter(context.color(R.color.colorPrimary))
    }

    override fun onViewAllClick() {
        context.startActivity(Intent(context, ActivityReadHistory::class.java))
    }

    override fun refresh(quranMeta: QuranMeta) {
        if (firstTime) {
            showLoader()
            firstTime = false
        }

        CoroutineScope(Dispatchers.IO).launch {
            val histories = dbHelper?.getAllHistories(10) ?: return@launch

            withContext(Dispatchers.Main) {
                hideLoader()
                if (histories.isEmpty()) {
                    makeNoHistoryAlert()
                } else {
                    if (adapter == null) {
                        adapter = ADPReadHistory(context, quranMeta, histories, Dimen.dp2px(context, 280f))
                        resolveListView().adapter = adapter
                    } else {
                        adapter!!.updateModels(histories)
                    }
                }
            }
        }
    }

    private fun makeNoHistoryAlert() {
        findViewById<View>(R.id.list).removeView()

        if (findViewById<View?>(R.id.text) != null) {
            return
        }

        addView(AppCompatTextView(context).apply {
            id = R.id.text
            updatePaddings(context.dp2px(20f), context.dp2px(15f))
            setTextSizePx(R.dimen.dmnCommonSize1_5)
            setTextColorResource(R.color.colorText2)
            setTypeface(Typeface.SANS_SERIF, Typeface.ITALIC)
            layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
            setText(R.string.strMsgReadShowupHere)
        })
    }

    override fun resolveListView(): RecyclerView {
        findViewById<View>(R.id.text).removeView()
        return super.resolveListView()
    }

}