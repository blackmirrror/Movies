package ru.blackmirrror.movies.app.presentation.services

import android.content.Context
import android.content.Intent
import androidx.core.app.JobIntentService
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject
import ru.blackmirrror.movies.domain.repositories.MoviesRepository

class CleanupService: JobIntentService() {

    private val repository: MoviesRepository by inject()
    override fun onHandleWork(intent: Intent) {
        cleanup()
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun cleanup() {
        GlobalScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.IO) {
                repository.removeCacheMovies()
            }
        }
    }

    companion object {
        const val JOB_ID = 123

        fun enqueueWork(context: Context, work: Intent) {
            enqueueWork(context, CleanupService::class.java, JOB_ID, work)
        }
    }
}