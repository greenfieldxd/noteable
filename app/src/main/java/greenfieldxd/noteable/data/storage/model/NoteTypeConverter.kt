package greenfieldxd.noteable.data.storage.model

import android.graphics.Color
import androidx.core.graphics.toColor
import androidx.room.TypeConverter

class NoteTypeConverter {
    @TypeConverter
    fun fromNoteType(value: NoteType): String = value.name

    @TypeConverter
    fun toNoteType(value: String): NoteType = NoteType.valueOf(value)

    @TypeConverter
    fun fromNoteColor(value: Color): Int = value.toArgb()

    @TypeConverter
    fun toNoteColor(value: Int): Color = value.toColor()
}