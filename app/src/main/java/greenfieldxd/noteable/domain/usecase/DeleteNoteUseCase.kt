package greenfieldxd.noteable.domain.usecase

import greenfieldxd.noteable.data.repository.NoteRepository
import greenfieldxd.noteable.domain.model.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DeleteNoteUseCase @Inject constructor(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(note: Note) = withContext(Dispatchers.IO) {
        try {
            repository.deleteNote(note)
        }
        catch (e: Exception) {
            e.printStackTrace()
        }
    }
} 