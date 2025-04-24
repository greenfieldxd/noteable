package greenfieldxd.noteable.data.storage.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import greenfieldxd.noteable.data.storage.model.NoteEntity
import greenfieldxd.noteable.data.storage.model.NoteEntity.Companion.NOTE_TABLE_NAME
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Query("SELECT * FROM $NOTE_TABLE_NAME")
    fun getAllNotes(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM $NOTE_TABLE_NAME WHERE id =:id")
    fun getNoteById(id: Long): Flow<NoteEntity?>

    @Insert
    suspend fun insertNote(noteEntity: NoteEntity)

    @Update
    suspend fun updateNote(noteEntity: NoteEntity)

    @Delete
    suspend fun deleteNote(noteEntity: NoteEntity)
}