package greenfieldxd.noteable.data.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import greenfieldxd.noteable.data.storage.dao.NoteDao
import greenfieldxd.noteable.data.storage.model.NoteEntity

@Database(entities = [NoteEntity::class], version = 1)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun noteDao() : NoteDao

    companion object {
        const val NOTE_DATABASE_NAME = "NOTE_DATABASE_NAME"
    }
}