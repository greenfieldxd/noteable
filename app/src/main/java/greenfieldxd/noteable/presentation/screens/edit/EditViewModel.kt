package greenfieldxd.noteable.presentation.screens.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import greenfieldxd.noteable.NoteApp
import greenfieldxd.noteable.domain.model.Note
import greenfieldxd.noteable.domain.model.defaultNote
import greenfieldxd.noteable.domain.usecase.DeleteNoteUseCase
import greenfieldxd.noteable.domain.usecase.GetNoteByIdUseCase
import greenfieldxd.noteable.domain.usecase.SaveNoteUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class EditScreenState {
    data object Empty: EditScreenState()
    data class Edit(val note: Note, val isNew: Boolean = false): EditScreenState()
}

sealed class EditScreenAction {
    data class OnTitleChanged(val title: String) : EditScreenAction()
    data class OnContentChanged(val content: String) : EditScreenAction()
    data object Save : EditScreenAction()
    data object Delete : EditScreenAction()
}

@HiltViewModel
class EditViewModel @Inject constructor(
    private val getNoteByIdUseCase: GetNoteByIdUseCase,
    private val saveNoteUseCase: SaveNoteUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase
) : ViewModel() {

    private val _noteState = MutableStateFlow<Note?>(null)

    val screenState: StateFlow<EditScreenState> = _noteState
        .map { note ->
            when {
                note == null -> EditScreenState.Empty
                note.id == 0L -> EditScreenState.Edit(note, isNew = true)
                else -> EditScreenState.Edit(note)
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = EditScreenState.Empty
        )

    fun setNoteId(id: Long?) {
        if (id == null) {
            _noteState.value = defaultNote()
        } else {
            viewModelScope.launch {
                getNoteByIdUseCase(id)
                    .collect { note ->
                        _noteState.value = note
                    }
            }
        }
    }

    fun dispatch(action: EditScreenAction) {
        when (action) {
            is EditScreenAction.OnTitleChanged -> updateTitle(action.title)
            is EditScreenAction.OnContentChanged -> updateContent(action.content)
            EditScreenAction.Save -> saveNote()
            EditScreenAction.Delete -> deleteNote()
        }
    }

    private fun updateTitle(title: String) {
        _noteState.update { it?.copy(title = title) }
    }

    private fun updateContent(title: String) {
        _noteState.update { it?.copy(content = title) }
    }

    private fun saveNote() {
        (screenState.value as? EditScreenState.Edit)?.let { state ->
            NoteApp.applicationScope.launch {
                _noteState.value?.let { note ->
                    saveNoteUseCase(note.copy(updatedAt = System.currentTimeMillis()), state.isNew)
                }
            }
        }
    }

    private fun deleteNote() {
        NoteApp.applicationScope.launch {
            _noteState.value?.let { note ->
                deleteNoteUseCase(note)
            }
        }
    }
}