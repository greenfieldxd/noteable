package greenfieldxd.noteable.data.storage.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.room.TypeConverter

class NoteTypeConverter {
    @TypeConverter
    fun fromNoteType(value: NoteType): String = value.name

    @TypeConverter
    fun toNoteType(value: String): NoteType = NoteType.valueOf(value)

    @TypeConverter
    fun fromNoteColor(value: Color): Int = value.toArgb()

    @TypeConverter
    fun toNoteColor(value: Int): Color = Color(value)
}
