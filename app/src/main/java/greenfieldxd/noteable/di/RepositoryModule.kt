package greenfieldxd.noteable.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import greenfieldxd.noteable.data.repository.NoteRepository
import greenfieldxd.noteable.data.repository.NoteRepositoryImpl
import greenfieldxd.noteable.data.storage.dao.NoteDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideFactRepository(dao: NoteDao): NoteRepository = NoteRepositoryImpl(dao)
}