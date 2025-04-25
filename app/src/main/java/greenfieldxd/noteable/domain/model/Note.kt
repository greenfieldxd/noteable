package greenfieldxd.noteable.domain.model

import androidx.compose.ui.graphics.Color
import greenfieldxd.noteable.data.storage.model.NoteType
import greenfieldxd.noteable.presentation.theme.NoteColors

data class Note(
    val id: Long,
    val title: String,
    val content: String,
    val pinned: Boolean,
    val noteType: NoteType,
    val color: Color,
    val updatedAt: Long
)

fun defaultNote() = Note(
    id = 0L,
    title = "",
    content = "",
    pinned = false,
    noteType = NoteType.Plain,
    color = NoteColors.All.random(),
    updatedAt = System.currentTimeMillis()
)
