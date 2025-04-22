package greenfieldxd.noteable.domain.model

import androidx.compose.ui.graphics.Color
import greenfieldxd.noteable.data.storage.model.NoteType

data class Note(
    val id: Long,
    val title: String,
    val content: String,
    val pinned: Boolean,
    val noteType: NoteType,
    val color: Color,
    val updatedAt: Long
)
