package com.brandyodhiambo.mynote.feature_notes.presenation.screens.note_dispaly

import com.brandyodhiambo.mynote.feature_notes.domain.model.Note
import com.brandyodhiambo.mynote.feature_notes.domain.utils.NoteOrder
import com.brandyodhiambo.mynote.feature_notes.domain.utils.OrderType

data class NotesState(
    val notes: List<Note> = emptyList(),
    val noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false
)