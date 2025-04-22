package greenfieldxd.noteable.presentation.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import greenfieldxd.noteable.presentation.theme.NoteableTheme

@Destination<RootGraph>(start = true)
@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel()
) {

}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    NoteableTheme {
        MainScreen()
    }
}