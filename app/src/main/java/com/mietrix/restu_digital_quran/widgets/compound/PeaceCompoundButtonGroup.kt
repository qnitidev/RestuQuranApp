package com.mietrix.restu_digital_quran.widgets.compound

import androidx.annotation.IdRes
import com.mietrix.restu_digital_quran.widgets.radio.PeaceRadioButton

interface PeaceCompoundButtonGroup {
    fun clearCheck()

    /**
     * Check a button without invoking the Listeners.
     *
     * @param id Id of the [PeaceRadioButton] to be checked.
     */
    fun checkSansInvocation(@IdRes id: Int)
    fun checkAtPosition(position: Int)
    fun check(@IdRes id: Int)
}
