package greenfieldxd.noteable.data.repository

import greenfieldxd.noteable.data.storage.dao.NoteDao
import greenfieldxd.noteable.data.storage.mapper.toNote
import greenfieldxd.noteable.data.storage.mapper.toNoteEntity
import greenfieldxd.noteable.domain.model.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface NoteRepository {
    fun getAllNotes(): Flow<List<Note>>
    fun getNoteById(id: Long): Flow<Note?>
    suspend fun insertNote(note: Note)
    suspend fun updateNote(note: Note)
    suspend fun deleteNote(note: Note)
}

class NoteRepositoryImpl @Inject constructor(
    private val noteDao: NoteDao
) : NoteRepository {
    override fun getAllNotes(): Flow<List<Note>> = noteDao.getAllNotes().map { entities ->
        entities.map { it.toNote() }
    }

    override fun getNoteById(id: Long): Flow<Note?> = noteDao.getNoteById(id).map { entity ->
        entity?.toNote()
    }

    override suspend fun insertNote(note: Note) {
        noteDao.insertNote(note.toNoteEntity())
    }

    override suspend fun updateNote(note: Note) {
        noteDao.updateNote(note.toNoteEntity())
    }

    override suspend fun deleteNote(note: Note) {
        noteDao.deleteNote(note.toNoteEntity())
    }
}