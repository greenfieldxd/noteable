package greenfieldxd.noteable.presentation.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import greenfieldxd.noteable.domain.model.Note
import greenfieldxd.noteable.domain.usecase.DeleteNoteUseCase
import greenfieldxd.noteable.domain.usecase.GetNotesUseCase
import greenfieldxd.noteable.domain.usecase.SaveNoteUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class MainScreenState {
    data object Empty: MainScreenState()
    data class Content(val notes: List<Note>): MainScreenState()
}

sealed class MainScreenAction {
    data class Delete(val id: Long) : MainScreenAction()
    data class Pin(val id: Long) : MainScreenAction()
}

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getNotesUseCase: GetNotesUseCase,
    private val saveNoteUseCase: SaveNoteUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase
) : ViewModel() {

    val screenState = getNotesUseCase()
        .map { notes ->
            if (notes.isEmpty()) MainScreenState.Empty
            else MainScreenState.Content(notes)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = MainScreenState.Empty
        )

    fun dispatch(action: MainScreenAction) {
        when (action) {
            is MainScreenAction.Delete -> deleteNote(action.id)
            is MainScreenAction.Pin -> pinNote(action.id)
        }
    }

    private fun deleteNote(id: Long) {
        (screenState.value as? MainScreenState.Content)?.let { contentState ->
            val noteToDelete = contentState.notes.find { it.id == id } ?: return
            
            viewModelScope.launch {
                deleteNoteUseCase(noteToDelete)
            }
        }
    }
    
    private fun pinNote(id: Long) {
        (screenState.value as? MainScreenState.Content)?.let { contentState ->
            val noteToPin = contentState.notes.find { it.id == id } ?: return
            val updatedNote = noteToPin.copy(pinned = !noteToPin.pinned)
            
            viewModelScope.launch {
                saveNoteUseCase(updatedNote, isNew = false)
            }
        }
    }
}