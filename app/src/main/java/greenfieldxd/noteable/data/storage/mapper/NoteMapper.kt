package greenfieldxd.noteable.data.storage.mapper

import greenfieldxd.noteable.data.storage.model.NoteEntity
import greenfieldxd.noteable.domain.model.Note

fun NoteEntity.toNote() = Note(
    id = id,
    title = title,
    content = content,
    pinned = pinned,
    noteType = noteType,
    color = color,
    updatedAt = updatedAt
)

fun Note.toNoteEntity() = NoteEntity(
    id = id,
    title = title,
    content = content,
    pinned = pinned,
    noteType = noteType,
    color = color,
    updatedAt = updatedAt
)