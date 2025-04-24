package greenfieldxd.noteable.domain.usecase

import greenfieldxd.noteable.data.repository.NoteRepository
import greenfieldxd.noteable.domain.model.Note
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNotesUseCase @Inject constructor(
    private val repository: NoteRepository
) {
    operator fun invoke(): Flow<List<Note>> {
        return repository.getAllNotes()
    }
} 