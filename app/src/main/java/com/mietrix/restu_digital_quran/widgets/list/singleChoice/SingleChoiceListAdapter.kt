package com.mietrix.restu_digital_quran.widgets.list.singleChoice

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.mietrix.restu_digital_quran.widgets.list.base.BaseListAdapter
import com.mietrix.restu_digital_quran.widgets.list.base.BaseListItem
import com.mietrix.restu_digital_quran.widgets.radio.PeaceRadioButton
import com.mietrix.restu_digital_quran.widgets.radio.PeaceRadioGroup

class SingleChoiceListAdapter(context: Context) : BaseListAdapter(context) {
    override fun onCreateItemView(item: BaseListItem, position: Int): View {
        val radio = PeaceRadioButton(context).apply {
            tag = item
            setTexts(item.label, item.message)
        }

        if (item.id != View.NO_ID) {
            radio.id = item.id
        }

        return radio
    }

    override fun onAppendItemView(container: ViewGroup, itemView: View, position: Int) {
        super.onAppendItemView(container, itemView, position)

        if (getItem(position).selected && container is PeaceRadioGroup) {
            container.check(itemView.id)
        }
    }
}
