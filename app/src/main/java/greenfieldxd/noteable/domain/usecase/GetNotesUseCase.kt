package greenfieldxd.noteable.domain.usecase

import greenfieldxd.noteable.data.repository.NoteRepository
import greenfieldxd.noteable.domain.model.Note
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetNotesUseCase @Inject constructor(
    private val repository: NoteRepository
) {
    operator fun invoke(): Flow<List<Note>> {
        return repository.getAllNotes().map { notes ->
            notes.sortedWith(
                compareByDescending<Note> { it.pinned }
                    .thenByDescending { it.updatedAt }
            )
        }
    }
} 