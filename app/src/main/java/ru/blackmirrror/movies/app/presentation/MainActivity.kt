package ru.blackmirrror.movies.app.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.blackmirrror.movies.R
import ru.blackmirrror.movies.app.presentation.services.CleanupService

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onDestroy() {
        val cleanupIntent = Intent(this, CleanupService::class.java)
        CleanupService.enqueueWork(this, cleanupIntent)
        super.onDestroy()
    }
}