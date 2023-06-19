package com.mietrix.restu_digital_quran.adapters.reference

import android.content.Context
import com.mietrix.restu_digital_quran.R
import com.mietrix.restu_digital_quran.components.quran.ExclusiveVerse
import com.mietrix.restu_digital_quran.databinding.LytQuranExclusiveVerseItemBinding
import com.mietrix.restu_digital_quran.utils.reader.factory.ReaderFactory

class ADPSolutionVerses(
    ctx: Context,
    itemWidth: Int,
    references: List<ExclusiveVerse>,
) : ADPExclusiveVerses(ctx, itemWidth, references) {

    override fun onBind(binding: LytQuranExclusiveVerseItemBinding, verse: ExclusiveVerse) {
        val context = binding.root.context
        val count = verse.verses.size

        binding.text.text = prepareTexts(
            verse.name,
            if (count > 1) context.getString(R.string.places, count)
            else context.getString(R.string.place, count),
            verse.inChapters
        )

        binding.root.setOnClickListener {
            val nameTitle = context.getString(R.string.strMsgReferenceInQuran, "\"" + verse.name + "\"")

            val description = context.getString(
                R.string.strMsgReferenceFoundPlaces,
                "\"" + verse.name + "\"",
                verse.verses.size
            )

            ReaderFactory.startReferenceVerse(
                context,
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