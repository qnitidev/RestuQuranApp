/*
 * Created by Faisal Khan on (c) 16/8/2021.
 */
package com.mietrix.restu_digital_quran.components.storageCleanup

data class ScriptCleanupItemModel(
    val scriptKey: String,
    val fontDownloadsCount: Int,
    var isCleared: Boolean = false
)
