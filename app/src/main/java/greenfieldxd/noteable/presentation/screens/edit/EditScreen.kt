package greenfieldxd.noteable.presentation.screens.edit

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator
import greenfieldxd.noteable.data.repository.FakeNoteRepository
import greenfieldxd.noteable.presentation.theme.NoteableTheme

@Destination<RootGraph>
@Composable
fun EditScreen(
    id: Long? = null,
    navigator: DestinationsNavigator,
    viewModel: EditViewModel = hiltViewModel()
) {

}

@Preview(showBackground = true)
@Composable
fun EditScreenPreview() {
    val fakeRepository = FakeNoteRepository()
    val fakeViewModel = EditViewModel(fakeRepository)

    NoteableTheme {
        EditScreen(navigator = EmptyDestinationsNavigator, viewModel = fakeViewModel)
    }
}