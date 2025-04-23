package greenfieldxd.noteable.presentation.screens.main

sealed class MainScreenAction {
    data class Delete(val id: Long): MainScreenAction()
    data class Pin(val id: Long, val value: Boolean): MainScreenAction()
}