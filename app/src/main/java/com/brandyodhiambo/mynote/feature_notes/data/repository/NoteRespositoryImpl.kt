package com.brandyodhiambo.mynote.feature_notes.data.repository

import com.brandyodhiambo.mynote.feature_notes.data.data_source.NoteDao
import com.brandyodhiambo.mynote.feature_notes.domain.model.Note
import com.brandyodhiambo.mynote.feature_notes.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class NoteRespositoryImpl(
    private val dao:NoteDao
):NoteRepository {
    override fun getNotes(): Flow<List<Note>> {
        return dao.getNotes()
    }

    override suspend fun getNoteById(id: Int): Note? {
        return dao.getNoteById(id)
    }

    override suspend fun insertNotes(note: Note) {
        dao.insertNote(note)
    }

    override suspend fun deleteNotes(note: Note) {
        dao.deleteNote(note)
    }
}