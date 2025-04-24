package greenfieldxd.noteable.presentation.navigation

import com.ramcosta.composedestinations.generated.destinations.EditScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import javax.inject.Inject

interface NoteNavigator {
    fun navigateToEditNote(id: Long? = null)
    fun navigateBack()
}

class NoteNavigatorImpl @Inject constructor(
    private val navigator: DestinationsNavigator
) : NoteNavigator {
    
    override fun navigateToEditNote(id: Long?) {
        navigator.navigate(EditScreenDestination(id = id))
    }
    
    override fun navigateBack() {
        navigator.popBackStack()
    }
} 