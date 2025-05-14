package greenfieldxd.noteable.di

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import greenfieldxd.noteable.NoteApp
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApplicationScope(app: Application): CoroutineScope {
        val noteApp = app as NoteApp
        return noteApp.applicationScope
    }
}

