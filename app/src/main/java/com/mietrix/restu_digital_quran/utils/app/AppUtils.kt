package com.mietrix.restu_digital_quran.utils.app

import com.mietrix.restu_digital_quran.utils.univ.FileUtils

object AppUtils {
    @JvmField
    val BASE_APP_DOWNLOADED_SAVED_DATA_DIR: String = FileUtils.createPath(
        "downloaded",
        "saved_data"
    )

    val BASE_APP_LOG_DATA_DIR = "logs"

    @JvmField
    val APP_OTHER_DIR: String = FileUtils.createPath(BASE_APP_DOWNLOADED_SAVED_DATA_DIR, "other")
}
