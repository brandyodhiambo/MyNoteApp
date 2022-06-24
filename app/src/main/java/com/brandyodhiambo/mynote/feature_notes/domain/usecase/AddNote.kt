package com.brandyodhiambo.mynote.feature_notes.domain.usecase

import com.brandyodhiambo.mynote.feature_notes.domain.model.InvalidNoteException
import com.brandyodhiambo.mynote.feature_notes.domain.model.Note
import com.brandyodhiambo.mynote.feature_notes.domain.repository.NoteRepository

class AddNote(
    private val repository: NoteRepository
) {

    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: Note){
        if (note.title.isBlank()){
            throw InvalidNoteException("Title of note cannot be empty")
        }
        if (note.description.isBlank()){
            throw InvalidNoteException("The description of note cannot be empty")
        }
        repository.insertNotes(note)
    }
}