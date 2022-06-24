package com.brandyodhiambo.mynote.feature_notes.domain.repository

import com.brandyodhiambo.mynote.feature_notes.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun getNotes(): Flow<List<Note>>

    suspend fun getNoteById(id:Int):Note?

    suspend fun insertNotes(note:Note)

    suspend fun deleteNotes(note:Note)
}