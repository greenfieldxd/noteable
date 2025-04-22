package greenfieldxd.noteable.di

import android.content.Context
import androidx.room.Room.databaseBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import greenfieldxd.noteable.data.storage.NoteDatabase
import greenfieldxd.noteable.data.storage.NoteDatabase.Companion.NOTE_DATABASE_NAME
import greenfieldxd.noteable.data.storage.dao.NoteDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): NoteDatabase =
        databaseBuilder(
            context,
            NoteDatabase::class.java, NOTE_DATABASE_NAME
        )
            .fallbackToDestructiveMigration(true)
            .build()

    @Provides
    fun provideNoteDao(db: NoteDatabase): NoteDao = db.noteDao()
}