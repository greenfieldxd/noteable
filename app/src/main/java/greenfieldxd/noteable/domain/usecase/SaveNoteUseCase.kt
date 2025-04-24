package greenfieldxd.noteable.domain.usecase

import greenfieldxd.noteable.data.repository.NoteRepository
import greenfieldxd.noteable.domain.model.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SaveNoteUseCase @Inject constructor(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(note: Note, isNew: Boolean) = withContext(Dispatchers.IO) {
        try {
            if (isNew) {
                repository.insertNote(note)
            } else {
                repository.updateNote(note)
            }
        }
        catch (e: Exception) {
            e.printStackTrace()
        }
    }
} 