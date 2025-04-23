package greenfieldxd.noteable.presentation.screens.main

import greenfieldxd.noteable.domain.model.Note

sealed class MainScreenState {
    data object Empty: MainScreenState()
    data class Content(val notes: List<Note>): MainScreenState()
}