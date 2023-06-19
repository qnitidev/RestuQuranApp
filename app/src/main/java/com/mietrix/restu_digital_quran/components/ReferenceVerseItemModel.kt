package com.mietrix.restu_digital_quran.components

import com.mietrix.restu_digital_quran.components.quran.subcomponents.Verse

class ReferenceVerseItemModel(
    val viewType: Int,
    val verse: Verse?,
    val chapterNo: Int,
    val fromVerse: Int,
    val toVerse: Int,
    val titleText: String?,
    var bookmarked: Boolean,
)
