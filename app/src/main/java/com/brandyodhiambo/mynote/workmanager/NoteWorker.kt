package com.brandyodhiambo.mynote.workmanager

import android.app.Notification
import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import com.brandyodhiambo.mynote.R
import com.brandyodhiambo.mynote.feature_notes.data.data_source.NotesDatabase
import com.brandyodhiambo.mynote.feature_notes.domain.model.Note
import com.brandyodhiambo.mynote.utils.Constants.NOTIFICATION_ID
import com.brandyodhiambo.mynote.utils.NotificationUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import timber.log.Timber

class NoteWorker @AssistedInject constructor (
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val notesDatabase: NotesDatabase,
    private val dispatcher: CoroutineDispatcher,
    private val firebaseAuth: FirebaseAuth,
    private val db: FirebaseFirestore
): CoroutineWorker(context, workerParams) {


 /*   override suspend fun getForegroundInfo(): ForegroundInfo {
        return ForegroundInfo(
            NOTIFICATION_ID, createNotification()
        )
    }

    private fun createNotification(): Notification {
       return NotificationUtils.sendNotification(
            applicationContext,
            applicationContext.getString(R.string.upload_starting)
        )

    }*/

    override suspend fun doWork(): Result {
        data class Notes(val notes: List<Note>)
        return try{

            val notes  = Notes(notesDatabase.noteDao.getNotes().flowOn(dispatcher).first())
            firebaseAuth.uid?.let { db.collection("data").document(it).set(notes).await() }
            //db.collection(firebaseAuth.uid?: "").document("data").set(notes)

            NotificationUtils.sendNotification(
                applicationContext,
                applicationContext.getString(R.string.upload_successful)
            )
            Result.success()
        } catch (e:java.lang.Exception){
            Timber.e(e)
            NotificationUtils.sendNotification(
                applicationContext,
                applicationContext.getString(R.string.upload_failed)
            )
            Result.failure()
        }

    }
}