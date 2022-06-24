package com.brandyodhiambo.mynote.di

import android.app.Application
import androidx.room.Room
import com.brandyodhiambo.mynote.feature_notes.data.data_source.NotesDatabase
import com.brandyodhiambo.mynote.feature_notes.data.repository.NoteRespositoryImpl
import com.brandyodhiambo.mynote.feature_notes.domain.repository.NoteRepository
import com.brandyodhiambo.mynote.feature_notes.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(app:Application): NotesDatabase {
        return Room.databaseBuilder(
            app,
            NotesDatabase::class.java,
            NotesDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(db: NotesDatabase): NoteRepository {
        return NoteRespositoryImpl(db.noteDao)
    }

    @Provides
    @Singleton
    fun provideNoteUseCase(repository: NoteRepository):NoteUseCase{
        return NoteUseCase(
            getNotes = GetNotes(repository),
            deleteNote = DeleteNote(repository),
            addNote = AddNote(repository),
            getNote = GetNote(repository)
        )
    }
}