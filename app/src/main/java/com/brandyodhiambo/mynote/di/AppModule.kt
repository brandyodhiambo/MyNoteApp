package com.brandyodhiambo.mynote.di

import android.app.Application
import com.brandyodhiambo.mynote.feature_auth.domain.usecase.validation.ValidateEmail
import com.brandyodhiambo.mynote.feature_auth.domain.usecase.validation.ValidatePassword
import com.brandyodhiambo.mynote.feature_notes.data.data_source.NotesDatabase
import com.brandyodhiambo.mynote.feature_notes.data.repository.NoteRespositoryImpl
import com.brandyodhiambo.mynote.feature_notes.domain.repository.NoteRepository
import com.brandyodhiambo.mynote.feature_notes.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDispatchers():CoroutineDispatcher{
        return Dispatchers.IO
    }

    @Provides
    @Singleton
    fun provideEmailValidation() = ValidateEmail()

    @Provides
    @Singleton
    fun providePasswordValidation() = ValidatePassword()
}