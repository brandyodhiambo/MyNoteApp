package com.brandyodhiambo.mynote.feature_notes.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.brandyodhiambo.mynote.ui.theme.blue
import com.brandyodhiambo.mynote.ui.theme.purple
import com.brandyodhiambo.mynote.ui.theme.teal

@Entity
data class Note(
    val title: String,
    val description: String,
    val timestamp:Long,
    val color:Int,
    @PrimaryKey val id: Int? = null
){
    companion object{
        val noteColors = listOf(purple, blue, teal)
    }
}
class InvalidNoteException(message: String): Exception(message)

