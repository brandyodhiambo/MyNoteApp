package com.brandyodhiambo.mynote.di

import com.brandyodhiambo.mynote.feature_auth.domain.repository.AuthRepository
import com.brandyodhiambo.mynote.feature_auth.domain.usecase.LoginUseCase
import com.brandyodhiambo.mynote.feature_auth.domain.usecase.SignUpUseCase
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
    fun provideLoginUseCase(authRepository: AuthRepository) = LoginUseCase(authRepository)

    @Provides
    @Singleton
    fun provideSignUpUseCase(authRepository: AuthRepository) = SignUpUseCase(authRepository)
}