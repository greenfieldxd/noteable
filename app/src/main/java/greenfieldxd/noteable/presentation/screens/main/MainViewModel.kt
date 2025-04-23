package greenfieldxd.noteable.presentation.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import greenfieldxd.noteable.data.repository.NoteRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val noteRepository: NoteRepository
) : ViewModel() {
    val screenState = noteRepository.getAllNotes()
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
            is MainScreenAction.Delete -> {
                (screenState.value as? MainScreenState.Content)?.let { contentState ->
                    val noteToDelete = contentState.notes.find { it.id == action.id }
                    noteToDelete?.let { note ->
                        viewModelScope.launch {
                            noteRepository.deleteNote(note)
                        }
                    }
                }
            }
            is MainScreenAction.Pin -> {
                (screenState.value as? MainScreenState.Content)?.let { contentState ->
                    val noteToPin = contentState.notes.find { it.id == action.id }
                    noteToPin?.let { note ->
                        val updatedNote = note.copy(pinned = action.value)
                        viewModelScope.launch {
                            noteRepository.updateNote(updatedNote)
                        }
                    }
                }
            }
        }
    }
}