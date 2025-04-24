package greenfieldxd.noteable.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import greenfieldxd.noteable.data.repository.NoteRepository
import greenfieldxd.noteable.domain.usecase.DeleteNoteUseCase
import greenfieldxd.noteable.domain.usecase.GetNoteByIdUseCase
import greenfieldxd.noteable.domain.usecase.GetNotesUseCase
import greenfieldxd.noteable.domain.usecase.SaveNoteUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {
    
    @Provides
    @Singleton
    fun provideGetNotesUseCase(repository: NoteRepository): GetNotesUseCase {
        return GetNotesUseCase(repository)
    }
    
    @Provides
    @Singleton
    fun provideGetNoteByIdUseCase(repository: NoteRepository): GetNoteByIdUseCase {
        return GetNoteByIdUseCase(repository)
    }
    
    @Provides
    @Singleton
    fun provideSaveNoteUseCase(repository: NoteRepository): SaveNoteUseCase {
        return SaveNoteUseCase(repository)
    }
    
    @Provides
    @Singleton
    fun provideDeleteNoteUseCase(repository: NoteRepository): DeleteNoteUseCase {
        return DeleteNoteUseCase(repository)
    }
} 