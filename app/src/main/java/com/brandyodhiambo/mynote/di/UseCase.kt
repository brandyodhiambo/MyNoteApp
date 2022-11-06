package com.brandyodhiambo.mynote.di

import com.brandyodhiambo.mynote.feature_auth.domain.repository.AuthRepository
import com.brandyodhiambo.mynote.feature_auth.domain.usecase.LoginUseCase
import com.brandyodhiambo.mynote.feature_auth.domain.usecase.SignUpUseCase
import com.brandyodhiambo.mynote.feature_notes.domain.repository.NoteRepository
import com.brandyodhiambo.mynote.feature_notes.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCase {

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
    fun provideLoginUseCase(authRepository: AuthRepository) = LoginUseCase(authRepository)

    @Provides
    @Singleton
    fun provideSignUpUseCase(authRepository: AuthRepository) = SignUpUseCase(authRepository)
}