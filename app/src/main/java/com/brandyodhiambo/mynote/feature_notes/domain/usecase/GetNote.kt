package com.brandyodhiambo.mynote.feature_notes.domain.usecase

import com.brandyodhiambo.mynote.feature_notes.domain.model.Note
import com.brandyodhiambo.mynote.feature_notes.domain.repository.NoteRepository

class GetNote(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(id:Int): Note?{
        return repository.getNoteById(id)
    }
}