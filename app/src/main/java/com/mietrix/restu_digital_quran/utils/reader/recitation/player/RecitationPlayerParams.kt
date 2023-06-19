package com.mietrix.restu_digital_quran.utils.reader.recitation.player

import android.content.Context
import android.net.Uri
import android.os.Parcel
import android.os.Parcelable
import androidx.core.os.ParcelCompat
import com.mietrix.restu_digital_quran.components.quran.QuranMeta
import com.mietrix.restu_digital_quran.components.reader.ChapterVersePair
import com.mietrix.restu_digital_quran.utils.reader.recitation.RecitationUtils
import com.mietrix.restu_digital_quran.utils.sharedPrefs.SPReader

class RecitationPlayerParams() : Parcelable {
    /**
     * Pair<surahNo, verseNo>
     */
    var currentVerse = ChapterVersePair(-1, -1)

    /**
     * Pair<surahNo, verseNo>
     */
    var firstVerse = ChapterVersePair(-1, -1)

    /**s
     * Pair<surahNo, verseNo>
     */
    var lastVerse = ChapterVersePair(-1, -1)

    var currentReciter: String? = null
    var currentTranslationReciter: String? = null
    var currentAudioOption = RecitationUtils.AUDIO_OPTION_DEFAULT
    var previouslyPlaying = false
    var pausedDueToHeadset = false
    var continueRange = true
    var repeatVerse = false
    var playbackSpeed = 1.0f
    var syncWithVerse = true

    var lastMediaURI: Uri? = null
    var lastTranslMediaURI: Uri? = null

    fun init(context: Context) {
        currentAudioOption = SPReader.getRecitationAudioOption(context)
        continueRange = SPReader.getRecitationContinueChapter(context)
        repeatVerse = SPReader.getRecitationRepeatVerse(context)
        playbackSpeed = SPReader.getRecitationSpeed(context)
        syncWithVerse = SPReader.getRecitationScrollSync(context)
    }

    constructor(parcel: Parcel) : this() {
        currentReciter = parcel.readString()
        previouslyPlaying = parcel.readByte() != 0.toByte()
        continueRange = parcel.readByte() != 0.toByte()
        repeatVerse = parcel.readByte() != 0.toByte()
        syncWithVerse = parcel.readByte() != 0.toByte()
        lastMediaURI = ParcelCompat.readParcelable(parcel, Uri::class.java.classLoader, Uri::class.java)
        lastTranslMediaURI = ParcelCompat.readParcelable(parcel, Uri::class.java.classLoader, Uri::class.java)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(currentReciter)
        parcel.writeByte(if (previouslyPlaying) 1 else 0)
        parcel.writeByte(if (continueRange) 1 else 0)
        parcel.writeByte(if (repeatVerse) 1 else 0)
        parcel.writeByte(if (syncWithVerse) 1 else 0)
        parcel.writeParcelable(lastMediaURI, flags)
        parcel.writeParcelable(lastTranslMediaURI, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<RecitationPlayerParams> {
        override fun createFromParcel(parcel: Parcel): RecitationPlayerParams {
            return RecitationPlayerParams(parcel)
        }

        override fun newArray(size: Int): Array<RecitationPlayerParams?> {
            return arrayOfNulls(size)
        }
    }

    fun getNextVerse(quranMeta: QuranMeta, curVerse: ChapterVersePair? = null): ChapterVersePair? {
        val currentVerse = curVerse ?: this.currentVerse

        val currentChapterNo = currentVerse.chapterNo
        val currentVerseNo = currentVerse.verseNo
        val lastChapterNo = lastVerse.chapterNo
        val lastVerseNo = lastVerse.verseNo

        if (!QuranMeta.isChapterValid(currentChapterNo) || !quranMeta.isVerseValid4Chapter(
                currentChapterNo,
                currentVerseNo
            )
        ) {
            return null
        }

        var nextChapterNo = currentChapterNo
        var nextVerseNo = currentVerseNo + 1

        if (nextVerseNo > quranMeta.getChapterVerseCount(currentChapterNo)) {
            // If we are at the last verse of the chapter, go to the first verse of the next chapter if possible.
            // Otherwise, stay at the current verse.
            nextVerseNo = if (QuranMeta.isChapterValid(nextChapterNo + 1)) {
                nextChapterNo++
                1
            } else {
                quranMeta.getChapterVerseCount(currentChapterNo)
            }
        }

        if (nextChapterNo > lastChapterNo || nextVerseNo > lastVerseNo) {
            nextChapterNo = -1
            nextVerseNo = -1
        }

        if (nextChapterNo == -1 && nextVerseNo == -1) {
            return null
        }

        return ChapterVersePair(nextChapterNo, nextVerseNo)
    }


    fun getPreviousVerse(quranMeta: QuranMeta): ChapterVersePair? {
        val currentChapterNo = currentVerse.chapterNo
        val currentVerseNo = currentVerse.verseNo
        val firstChapterNo = firstVerse.chapterNo
        val firstVerseNo = firstVerse.verseNo

        if (!QuranMeta.isChapterValid(currentChapterNo) || !quranMeta.isVerseValid4Chapter(
                currentChapterNo,
                currentVerseNo
            )
        ) {
            return null
        }

        var previousChapterNo = currentChapterNo
        var previousVerseNo = currentVerseNo - 1
        if (previousVerseNo < 1) {
            // If we are at the first verse of the chapter, go to the last verse of the previous chapter if possible.
            // Otherwise, stay at the current verse.
            previousVerseNo = if (QuranMeta.isChapterValid(previousChapterNo - 1)) {
                previousChapterNo--
                quranMeta.getChapterVerseCount(previousChapterNo)
            } else {
                1
            }
        }

        // If the previous verse or chapter goes beyond the current range,
        // then return -1 for both so that the player doesn't change anything.
        if (previousChapterNo < firstChapterNo || previousVerseNo < firstVerseNo) {
            previousChapterNo = -1
            previousVerseNo = -1
        }

        if (previousChapterNo == -1 && previousVerseNo == -1) {
            return null
        }

        return ChapterVersePair(previousChapterNo, previousVerseNo)
    }

    /**
     * @return Returns true if the player has previous verse within the current playable verse range.
     */
    fun hasNextVerse(quranMeta: QuranMeta): Boolean {
        val currentChapterNo = currentVerse.chapterNo
        val currentVerseNo = currentVerse.verseNo
        val lastChapterNo = lastVerse.chapterNo
        val lastVerseNo = lastVerse.verseNo

        if (
            !QuranMeta.isChapterValid(currentChapterNo) ||
            !quranMeta.isVerseValid4Chapter(currentChapterNo, currentVerseNo)
        ) {
            return false
        }

        var nextChapterNo = currentChapterNo
        var nextVerseNo = currentVerseNo + 1

        if (nextVerseNo > quranMeta.getChapterVerseCount(currentChapterNo)) {
            nextChapterNo += 1
            nextVerseNo = 1
        }

        return nextChapterNo <= lastChapterNo && nextVerseNo <= lastVerseNo
    }

    /**
     * @return Returns true if the player has previous verse within the current playable verse range.
     */
    fun hasPreviousVerse(quranMeta: QuranMeta): Boolean {
        val currentChapterNo = currentVerse.chapterNo
        val currentVerseNo = currentVerse.verseNo
        val firstChapterNo = firstVerse.chapterNo
        val firstVerseNo = firstVerse.verseNo

        if (
            !QuranMeta.isChapterValid(currentChapterNo) ||
            !quranMeta.isVerseValid4Chapter(currentChapterNo, currentVerseNo)
        ) {
            return false
        }

        var prevChapterNo = currentChapterNo
        var prevVerseNo = currentVerseNo - 1

        if (prevVerseNo < 1) {
            prevChapterNo -= 1
            prevVerseNo = quranMeta.getChapterVerseCount(prevChapterNo)
        }

        return prevChapterNo >= firstChapterNo && prevVerseNo >= firstVerseNo
    }

    fun isCurrentVerse(verse: Pair<Int, Int>): Boolean {
        return isCurrentVerse(verse.first, verse.second)
    }

    fun isCurrentVerse(chapterNo: Int, verseNo: Int): Boolean {
        return currentVerse.chapterNo == chapterNo && currentVerse.verseNo == verseNo
    }

    val currentChapterNo get() = currentVerse.chapterNo
    val currentVerseNo get() = currentVerse.verseNo
}