package greenfieldxd.noteable.presentation.screens.edit

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator
import greenfieldxd.noteable.R
import greenfieldxd.noteable.data.repository.FakeNoteRepository
import greenfieldxd.noteable.domain.usecase.DeleteNoteUseCase
import greenfieldxd.noteable.domain.usecase.GetNoteByIdUseCase
import greenfieldxd.noteable.domain.usecase.SaveNoteUseCase
import greenfieldxd.noteable.presentation.screens.components.CustomTextField
import greenfieldxd.noteable.presentation.screens.navigation.DefaultScreenTransitions
import greenfieldxd.noteable.presentation.theme.NoteableTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

@OptIn(ExperimentalMaterial3Api::class)
@Destination<RootGraph>(style = DefaultScreenTransitions::class)
@Composable
fun EditScreen(
    id: Long? = null,
    backgroundColor: Color? = null,
    navigator: DestinationsNavigator,
    viewModel: EditViewModel = hiltViewModel()
) {
    val screenState by viewModel.screenState.collectAsStateWithLifecycle()

    val (note, isNew, canDelete) = when (val state = screenState) {
        is EditScreenState.Edit -> Triple(state.note, state.isNew, !state.isNew)
        is EditScreenState.Empty -> Triple(null, true, false)
    }

    LaunchedEffect(id) {
        viewModel.setNoteId(id)
    }

    BackHandler {
        viewModel.dispatch(EditScreenAction.Save)
        navigator.popBackStack()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    CustomTextField(
                        value = note?.title ?: "",
                        onValueChanged = { viewModel.dispatch(EditScreenAction.OnTitleChanged(it)) },
                        placeholderText = if (isNew) stringResource(R.string.placeholder_new_note) else stringResource(R.string.placeholder_note),
                        textStyle = MaterialTheme.typography.headlineLarge
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = backgroundColor ?: MaterialTheme.colorScheme.background),
                navigationIcon = {
                    IconButton(onClick = {
                        viewModel.dispatch(EditScreenAction.Save)
                        navigator.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null,
                        )
                    }
                },
                actions = {
                    if (canDelete) {
                        IconButton(
                            onClick = {
                                viewModel.dispatch(EditScreenAction.Delete)
                                navigator.popBackStack()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = null,
                            )
                        }
                    }
                },
            )
        },
        content = { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = backgroundColor ?: MaterialTheme.colorScheme.background)
            ) {
                Column (
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(16.dp)
                ) {
                    CustomTextField(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 8.dp),
                        value = note?.content ?: "",
                        onValueChanged = { viewModel.dispatch(EditScreenAction.OnContentChanged(it)) },
                        placeholderText = stringResource(R.string.placeholder_content),
                        textStyle = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun EditScreenPreview() {
    val fakeRepository = FakeNoteRepository()
    val viewModel = EditViewModel(
        applicationScope = CoroutineScope(Dispatchers.Default + SupervisorJob()),
        getNoteByIdUseCase = GetNoteByIdUseCase(fakeRepository),
        saveNoteUseCase = SaveNoteUseCase(fakeRepository),
        deleteNoteUseCase = DeleteNoteUseCase(fakeRepository)
    )

    NoteableTheme {
        EditScreen(
            navigator = EmptyDestinationsNavigator, 
            viewModel = viewModel
        )
    }
}