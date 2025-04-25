package greenfieldxd.noteable.data.storage.model

import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = NoteEntity.Companion.NOTE_TABLE_NAME)
@TypeConverters(NoteTypeConverter::class)
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val title: String,
    val content: String,
    val pinned: Boolean,
    val noteType: NoteType,
    val color: Color,
    val updatedAt: Long
) {
    companion object {
        const val NOTE_TABLE_NAME = "NOTE_TABLE_NAME"
    }
}
