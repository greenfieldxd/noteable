package greenfieldxd.noteable.presentation.screens.edit

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination<RootGraph>
@Composable
fun EditScreen(
    navigator: DestinationsNavigator,
    viewModel: EditViewModel = hiltViewModel()
) {

}