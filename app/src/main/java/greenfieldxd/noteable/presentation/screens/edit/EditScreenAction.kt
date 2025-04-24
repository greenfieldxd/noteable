package greenfieldxd.noteable.presentation.screens.edit

sealed class EditScreenAction {
    data object Save: EditScreenAction()
    data object Delete: EditScreenAction()
    data class OnTitleChanged(val title: String): EditScreenAction()
    data class OnContentChanged(val content: String): EditScreenAction()
}