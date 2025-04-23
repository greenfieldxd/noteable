package greenfieldxd.noteable.presentation.screens.edit

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import greenfieldxd.noteable.data.repository.NoteRepository
import javax.inject.Inject

@HiltViewModel
class EditViewModel @Inject constructor(
    private val noteRepository: NoteRepository
) : ViewModel() {

}