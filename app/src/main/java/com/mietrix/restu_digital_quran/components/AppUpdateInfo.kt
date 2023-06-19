package com.mietrix.restu_digital_quran.components

import android.content.Context
import com.mietrix.restu_digital_quran.BuildConfig
import com.mietrix.restu_digital_quran.api.JsonHelper
import com.mietrix.restu_digital_quran.api.models.AppUpdate
import com.mietrix.restu_digital_quran.utils.univ.FileUtils
import kotlinx.serialization.decodeFromString

class AppUpdateInfo(private val ctx: Context) {
    companion object {
        const val CRITICAL = 5
        const val MAJOR = 4
        const val MODERATE = 3
        const val MINOR = 2
        const val COSMETIC = 1
        const val NONE = 0
    }

    private val updates: List<AppUpdate> = try {
        val fileUtils = FileUtils.newInstance(ctx)
        JsonHelper.json.decodeFromString(fileUtils.appUpdatesFile.readText())
    } catch (e: Exception) {
        ArrayList()
    }

    fun getMostImportantUpdate(): AppUpdate {
        val currentAppVersion = BuildConfig.VERSION_CODE

        val mostImportantUpdate = updates
            .filter { it.version > currentAppVersion }
            .sortedBy { it.priority }
        return mostImportantUpdate.takeIf { it.isNotEmpty() }?.get(0) ?: AppUpdate(0, NONE)
    }
}
