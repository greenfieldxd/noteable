package greenfieldxd.noteable.presentation.screens.edit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator
import greenfieldxd.noteable.data.repository.FakeNoteRepository
import greenfieldxd.noteable.presentation.screens.DefaultScreenTransitions
import greenfieldxd.noteable.presentation.theme.NoteableTheme

@OptIn(ExperimentalMaterial3Api::class)
@Destination<RootGraph>(style = DefaultScreenTransitions::class)
@Composable
fun EditScreen(
    id: Long? = null,
    navigator: DestinationsNavigator,
    viewModel: EditViewModel = hiltViewModel()
) {
    val screenState by viewModel.screenState.collectAsState()
    val note = when (val state = screenState) {
        is EditScreenState.Loading -> null
        is EditScreenState.Create -> state.note
        is EditScreenState.Edit -> state.note
    }

    LaunchedEffect(Unit) {
        viewModel.setNoteId(id)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    BasicTextField(
                        textStyle = MaterialTheme.typography.titleLarge,
                        value = note?.title ?: "",
                        onValueChange = { viewModel.dispatch(EditScreenAction.OnTitleChanged(it)) }
                    )
                },
                actions = {
                    IconButton(
                        onClick = {
                            viewModel.dispatch(EditScreenAction.Save)
                            navigator.popBackStack()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null
                        )
                    }
                    if (screenState is EditScreenState.Edit) {
                        IconButton(
                            onClick = {
                                viewModel.dispatch(EditScreenAction.Delete)
                                navigator.popBackStack()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = null
                            )
                        }
                    }
                }
            )
        },
        content = { innerPadding ->
            Column (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp)
            ) {
                BasicTextField(
                    modifier = Modifier.fillMaxSize(),
                    value = note?.content ?: "",
                    onValueChange = { viewModel.dispatch(EditScreenAction.OnContentChanged(it)) }
                )
            }
        }
    )
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