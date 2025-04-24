package greenfieldxd.noteable.presentation.screens.edit

import greenfieldxd.noteable.domain.model.Note

sealed class EditScreenState {
    data object Loading: EditScreenState()
    data class Create(val note: Note): EditScreenState()
    data class Edit(val note: Note): EditScreenState()
}