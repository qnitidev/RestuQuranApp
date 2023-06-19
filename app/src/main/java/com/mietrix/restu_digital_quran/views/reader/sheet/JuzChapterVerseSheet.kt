package com.mietrix.restu_digital_quran.views.reader.sheet

import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.mietrix.restu_digital_quran.widgets.bottomSheet.PeaceBottomSheet

class JuzChapterVerseSheet : PeaceBottomSheet() {
    override fun onCreate(savedInstanceState: Bundle?) {
        params.apply {
            initialBehaviorState = BottomSheetBehavior.STATE_EXPANDED
            disableDragging = true
            fullHeight = false
            windowDimAmount = .9F
        }

        super.onCreate(savedInstanceState)
    }
}
