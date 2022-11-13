package com.brandyodhiambo.mynote.workmanager

import android.app.Notification
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.brandyodhiambo.mynote.R
import com.brandyodhiambo.mynote.feature_notes.data.data_source.NotesDatabase
import com.brandyodhiambo.mynote.feature_notes.domain.model.Note
import com.brandyodhiambo.mynote.workmanager.utils.Constants.NOTIFICATION_CHANNEL_ID
import com.brandyodhiambo.mynote.workmanager.utils.WorkerKeys.ERROR_MESSAGE
import com.brandyodhiambo.mynote.workmanager.utils.WorkerKeys.UPLOAD_URI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import java.net.HttpRetryException
import javax.inject.Singleton
import kotlin.random.Random

@HiltWorker
class NoteWorker @AssistedInject  constructor (
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val notesDatabase: NotesDatabase,
    private val firebaseAuth: FirebaseAuth,
   private val db: FirebaseFirestore
): CoroutineWorker(context, workerParams) {

    companion object{
        const val WORK_NAME = "UPLOAD_WORK"
        private const val TAG = "UploadWorker"
    }
    override suspend fun doWork(): Result {
        return try{
            setForegroundService(context = applicationContext, message = applicationContext.getString(R.string.upload_starting))

            val notes  = Notes(notesDatabase.noteDao.getNotes().flowOn(Dispatchers.IO).first())
            firebaseAuth.uid?.let { db.collection("data").document(it).set(notes).await() }

            setForegroundService(context = applicationContext, message = applicationContext.getString(R.string.upload_successful))

            Result.success(
                workDataOf(
                    UPLOAD_URI to notes.notes.size
                )
            )
        } catch (e:Exception){
            Timber.e(e)
            setForegroundService(context = applicationContext, message = applicationContext.getString(R.string.upload_failed))
            Result.failure(
                workDataOf(
                    ERROR_MESSAGE to e.message
                )
            )
        } catch (e:HttpRetryException){
            Timber.e(e)
            return Result.retry()
        }
        catch (e:Throwable){
            Timber.e(e)
            return Result.retry()
        }
    }


    private suspend fun setForegroundService(context: Context, message:String) {
        setForeground(
            ForegroundInfo(
                Random.nextInt(),
                NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
                    .setColor(ContextCompat.getColor(context, R.color.teal_200))
                    .setSmallIcon(R.drawable.ic_notifications)
                    .setStyle(
                        NotificationCompat
                            .BigTextStyle()
                            .bigText(message)
                    )

                    .setContentTitle(context.getString(R.string.app_name))
                    .setContentText(message)
                    .setDefaults(Notification.DEFAULT_VIBRATE)
                    .setAutoCancel(true)
                    .build()
            )
        )
    }
}

data class Notes(val notes: List<Note>)
