package com.mietrix.restu_digital_quran.vh.search

import com.mietrix.restu_digital_quran.components.search.JuzJumpModel
import com.mietrix.restu_digital_quran.components.search.SearchResultModelBase
import com.mietrix.restu_digital_quran.databinding.LytReaderJuzSpinnerItemBinding
import com.mietrix.restu_digital_quran.utils.reader.factory.ReaderFactory.startJuz

class VHJuzJump(private val mBinding: LytReaderJuzSpinnerItemBinding, applyMargins: Boolean) : VHSearchResultBase(
    mBinding.root
) {
    init {
        setupJumperView(mBinding.root, applyMargins)
    }

    override fun bind(model: SearchResultModelBase, pos: Int) {
        (model as JuzJumpModel).apply {
            mBinding.juzSerial.text = model.juzSerial
            mBinding.root.setOnClickListener { startJuz(it.context, model.juzNo) }
        }
    }
}
