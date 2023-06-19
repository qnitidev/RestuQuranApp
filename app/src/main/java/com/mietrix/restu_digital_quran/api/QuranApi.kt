package com.mietrix.restu_digital_quran.api

import com.mietrix.restu_digital_quran.api.models.tafsir.TafsirModel
import retrofit2.http.GET
import retrofit2.http.Path

interface QuranApi {
    @GET("api/qdc/tafsirs/{slug}/by_ayah/{verseKey}")
    suspend fun getTafsir(
        @Path("slug") slug: String,
        @Path("verseKey") verseKey: String
    ): Map<String, TafsirModel>
}
