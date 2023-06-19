package com.mietrix.restu_digital_quran.adapters.reference

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mietrix.restu_digital_quran.R
import com.mietrix.restu_digital_quran.components.quran.ExclusiveVerse
import com.mietrix.restu_digital_quran.databinding.LytQuranEtiquetteVerseItemBinding
import com.mietrix.restu_digital_quran.utils.gesture.HoverPushEffect
import com.mietrix.restu_digital_quran.utils.gesture.HoverPushOpacityEffect
import com.mietrix.restu_digital_quran.utils.reader.factory.ReaderFactory

class ADPEtiquette(
    private val references: List<ExclusiveVerse>
) : RecyclerView.Adapter<ADPEtiquette.VHEtiquetteVerse>() {
    override fun getItemCount(): Int {
        return references.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHEtiquetteVerse {
        return VHEtiquetteVerse(
            LytQuranEtiquetteVerseItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: VHEtiquetteVerse, position: Int) {
        holder.bind(references[position])
    }

    inner class VHEtiquetteVerse(private val binding: LytQuranEtiquetteVerseItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.apply {
                setOnTouchListener(HoverPushOpacityEffect(HoverPushEffect.Pressure.LOW))
                setBackgroundResource(R.drawable.dr_bg_search_result_item)
            }
        }

        fun bind(verse: ExclusiveVerse) {
            binding.text.text = verse.name

            binding.root.setOnClickListener {
                // Take the first reference as it has only one verse
                val reference = verse.verses.first()
                ReaderFactory.startVerseRange(
                    it.context,
                    reference.first,
                    reference.second,
                    reference.third,
                )
            }
        }
    }
}