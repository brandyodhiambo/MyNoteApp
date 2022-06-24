package com.brandyodhiambo.mynote.feature_notes.presenation.screens.note_dispaly

import com.brandyodhiambo.mynote.feature_notes.domain.model.Note
import com.brandyodhiambo.mynote.feature_notes.domain.utils.NoteOrder

sealed class NotesEvent {
    data class Order(val noteOrder: NoteOrder): NotesEvent()
    data class DeleteNote(val note: Note): NotesEvent()
    object RestoreNote: NotesEvent()
    object ToggleOrderSection: NotesEvent()
}