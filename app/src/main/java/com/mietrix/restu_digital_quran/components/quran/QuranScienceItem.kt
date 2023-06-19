package com.mietrix.restu_digital_quran.components.quran

import java.io.Serializable

data class QuranScienceItem(
    val title: String,
    val referencesCount: Int,
    val path: String,
    val drawableRes: Int,
) : Serializable