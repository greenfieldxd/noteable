package greenfieldxd.noteable.data.repository

import androidx.compose.ui.graphics.Color
import greenfieldxd.noteable.data.storage.model.NoteType
import greenfieldxd.noteable.domain.model.Note
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

class FakeNoteRepository : NoteRepository {

    private val notesFlow = MutableStateFlow<List<Note>>(listOf(
        Note(
            id = 1, title = "Первая заметка",
            content = "Контент первой заметки",
            pinned = false,
            noteType = NoteType.Plain,
            color = Color.Blue,
            updatedAt = System.currentTimeMillis()
        ),
        Note(
            id = 2, title = "Вторая заметка",
            content = "Контент второй заметки",
            pinned = true,
            noteType = NoteType.Plain,
            color = Color.Yellow,
            updatedAt = System.currentTimeMillis()
        )
    ))

    override fun getAllNotes(): Flow<List<Note>> = notesFlow

    override fun getNoteById(id: Long): Flow<Note> = notesFlow.map { notes ->
        notes.find { it.id == id } ?: error("Note with id $id not found")
    }

    override suspend fun insertNote(note: Note) {
        val newList = notesFlow.value.toMutableList().apply { add(note) }
        notesFlow.value = newList
    }

    override suspend fun updateNote(note: Note) {
        val newList = notesFlow.value.toMutableList()
        val index = newList.indexOfFirst { it.id == note.id }
        if (index != -1) {
            newList[index] = note
            notesFlow.value = newList
        }
    }

    override suspend fun deleteNote(note: Note) {
        val newList = notesFlow.value.toMutableList().apply {
            removeAll { it.id == note.id }
        }
        notesFlow.value = newList
    }
}