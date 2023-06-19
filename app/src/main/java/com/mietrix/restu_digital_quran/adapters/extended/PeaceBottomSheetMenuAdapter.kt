package com.mietrix.restu_digital_quran.adapters.extended

import android.content.Context
import android.view.View
import com.mietrix.restu_digital_quran.R
import com.mietrix.restu_digital_quran.utils.extensions.color
import com.mietrix.restu_digital_quran.utils.extensions.dp2px
import com.mietrix.restu_digital_quran.utils.extensions.updatePaddingVertical
import com.mietrix.restu_digital_quran.widgets.list.base.BaseListAdapter
import com.mietrix.restu_digital_quran.widgets.list.base.BaseListItem
import com.mietrix.restu_digital_quran.widgets.list.base.BaseListItemView

class PeaceBottomSheetMenuAdapter(context: Context) : BaseListAdapter(context) {
    private val mMessageColor = context.color(R.color.colorText2)

    override fun onCreateItemView(item: BaseListItem, position: Int): View {
        val view = super.onCreateItemView(item, position) as BaseListItemView

        if (item.message.isNullOrEmpty()) {
            view.containerView.updatePaddingVertical(context.dp2px(3f))
        } else {
            view.messageView?.setTextColor(mMessageColor)
        }

        return view
    }
}
