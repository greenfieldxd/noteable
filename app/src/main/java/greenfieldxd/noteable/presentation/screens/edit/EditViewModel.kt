package greenfieldxd.noteable.presentation.screens.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import greenfieldxd.noteable.data.repository.NoteRepository
import greenfieldxd.noteable.domain.model.Note
import greenfieldxd.noteable.domain.model.defaultNote
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditViewModel @Inject constructor(
    private val noteRepository: NoteRepository
) : ViewModel() {

    private val _noteState = MutableStateFlow<Note?>(null)

    val screenState: StateFlow<EditScreenState> = _noteState
        .map { note ->
            when {
                note == null -> EditScreenState.Loading
                note.id == 0L -> EditScreenState.Create(note)
                else -> EditScreenState.Edit(note)
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = EditScreenState.Loading
        )

    fun setNoteId(id: Long?) {
        if (id == null) {
            _noteState.value = defaultNote()
        } else {
            viewModelScope.launch {
                noteRepository.getNoteById(id)
                    .collect { note ->
                        _noteState.value = note
                    }
            }
        }
    }

    fun dispatch(action: EditScreenAction) {
        when (action) {
            is EditScreenAction.OnContentChanged -> {
                _noteState.update { it?.copy(content = action.content) }
            }
            is EditScreenAction.OnTitleChanged -> {
                _noteState.update { it?.copy(title = action.title) }
            }
            EditScreenAction.Save -> {
                viewModelScope.launch {
                    _noteState.value?.let { note ->
                        if (note.id != 0L) {
                            noteRepository.updateNote(note)
                        } else {
                            noteRepository.insertNote(note)
                        }
                    }
                }
            }
            EditScreenAction.Delete -> {
                viewModelScope.launch {
                    if ((_noteState.value?.id ?: 0L) != 0L) {
                        _noteState.value?.let { note ->
                            noteRepository.deleteNote(note)
                        }
                    }
                }
            }
        }
    }
}