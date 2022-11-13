package com.brandyodhiambo.mynote.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.work.*
import com.brandyodhiambo.mynote.NavGraphs
import com.brandyodhiambo.mynote.feature_notes.data.data_source.NotesDatabase
import com.brandyodhiambo.mynote.ui.theme.MyNoteTheme
import com.brandyodhiambo.mynote.workmanager.utils.WorkerKeys.UPLOAD_URI
import com.brandyodhiambo.mynote.workmanager.NoteWorker
import com.brandyodhiambo.mynote.workmanager.NoteWorker.Companion.WORK_NAME
import com.brandyodhiambo.mynote.workmanager.startPeriodicWorkRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.ramcosta.composedestinations.DestinationsNavHost
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyNoteTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val context = LocalContext.current

                    startPeriodicWorkRequest(context)
                    Scaffold() {
                        DestinationsNavHost(
                            navGraph = NavGraphs.root,
                        )
                    }

                }
            }
        }
    }
}

