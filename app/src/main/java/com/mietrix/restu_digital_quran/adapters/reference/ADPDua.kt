package com.mietrix.restu_digital_quran.adapters.reference

import android.content.Context
import com.mietrix.restu_digital_quran.R
import com.mietrix.restu_digital_quran.components.quran.ExclusiveVerse
import com.mietrix.restu_digital_quran.databinding.LytQuranExclusiveVerseItemBinding
import com.mietrix.restu_digital_quran.utils.reader.factory.ReaderFactory

class ADPDua(
    ctx: Context,
    itemWidth: Int,
    references: List<ExclusiveVerse>,
) : ADPExclusiveVerses(ctx, itemWidth, references) {

    override fun onBind(binding: LytQuranExclusiveVerseItemBinding, verse: ExclusiveVerse) {
        val ctx = binding.root.context
        val excluded = verse.id in arrayOf(1, 7)

        val duaName = if (!excluded) ctx.getString(R.string.strMsgDuaFor, verse.name)
        else verse.name

        val count = verse.verses.size

        binding.text.text = prepareTexts(
            duaName,
            if (count > 1) ctx.getString(R.string.places, count)
            else ctx.getString(R.string.place, count),
            verse.inChapters
        )

        binding.root.setOnClickListener {
            val nameTitle = if (!excluded) ctx.getString(R.string.strMsgDuaFor, verse.name)
            else ctx.getString(R.string.strMsgReferenceInQuran, "\"" + verse.name + "\"")

            val description = ctx.getString(
                R.string.strMsgReferenceFoundPlaces,
                if (excluded) nameTitle else "\"" + nameTitle + "\"",
                verse.verses.size
            )

            ReaderFactory.startReferenceVerse(
                ctx,
                true,
                nameTitle,
                description,
                arrayOf(),
                verse.chapters,
                verse.versesRaw
            )
        }
    }
}