package com.brandyodhiambo.mynote.di

import com.brandyodhiambo.mynote.feature_auth.data.repository.AuthRepostoryImpl
import com.brandyodhiambo.mynote.feature_auth.domain.repository.AuthRepository
import com.brandyodhiambo.mynote.feature_auth.domain.usecase.LoginUseCase
import com.brandyodhiambo.mynote.feature_auth.domain.usecase.SignUpUseCase
import com.brandyodhiambo.mynote.feature_notes.data.data_source.NotesDatabase
import com.brandyodhiambo.mynote.feature_notes.data.repository.NoteRespositoryImpl
import com.brandyodhiambo.mynote.feature_notes.domain.repository.NoteRepository
import com.brandyodhiambo.mynote.feature_notes.domain.usecase.*
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideNoteRepository(db: NotesDatabase): NoteRepository {
        return NoteRespositoryImpl(db.noteDao)
    }

    @Provides
    @Singleton
    fun provideNoteUseCase(repository: NoteRepository): NoteUseCase {
        return NoteUseCase(
            getNotes = GetNotes(repository),
            deleteNote = DeleteNote(repository),
            addNote = AddNote(repository),
            getNote = GetNote(repository)
        )
    }
    @Provides
    @Singleton
    fun provideAuthRepository(firebaseAuth: FirebaseAuth):AuthRepository{
        return AuthRepostoryImpl(firebaseAuth)
    }

    @Provides
    @Singleton
    fun provideLoginUseCase(authRepository: AuthRepository):LoginUseCase{
        return LoginUseCase(authRepository)
    }

    @Provides
    @Singleton
    fun provideSignUpUseCase(authRepository: AuthRepository):SignUpUseCase{
        return SignUpUseCase(authRepository)
    }
}