package greenfieldxd.noteable.presentation.screens.edit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
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
import greenfieldxd.noteable.presentation.screens.navigation.DefaultScreenTransitions
import greenfieldxd.noteable.presentation.screens.components.CustomTextField
import greenfieldxd.noteable.presentation.theme.NoteableTheme

@OptIn(ExperimentalMaterial3Api::class)
@Destination<RootGraph>(style = DefaultScreenTransitions::class)
@Composable
fun EditScreen(
    id: Long? = null,
    navigator: DestinationsNavigator,
    viewModel: EditViewModel = hiltViewModel()
) {
    val screenState by viewModel.screenState.collectAsStateWithLifecycle()

    var canDelete = false
    var isNew = false
    val note = when (val state = screenState) {
        is EditScreenState.Empty -> null
        is EditScreenState.Edit -> {
            canDelete = !state.isNew
            isNew = state.isNew
            state.note
        }
    }

    LaunchedEffect(Unit) {
        viewModel.setNoteId(id)
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
                colors = TopAppBarDefaults.topAppBarColors(containerColor = note?.color ?: MaterialTheme.colorScheme.background),
                navigationIcon = {
                    IconButton(onClick = { navigator.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null,
                        )
                    }
                },
                actions = {
                    IconButton(
                        enabled = note?.let { it.title.isNotBlank() || it.content.isNotBlank() } == true,
                        onClick = {
                            viewModel.dispatch(EditScreenAction.Save)
                            navigator.popBackStack()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                        )
                    }
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
                modifier = Modifier.fillMaxSize()
                    .background(color = note?.color ?: MaterialTheme.colorScheme.background)
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