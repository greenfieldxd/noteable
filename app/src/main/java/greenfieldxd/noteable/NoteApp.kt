package greenfieldxd.noteable

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

@HiltAndroidApp
class NoteApp : Application() {

    companion object {
        val applicationScope = CoroutineScope(Dispatchers.Default + SupervisorJob())
    }
}