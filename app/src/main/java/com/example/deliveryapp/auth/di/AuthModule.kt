package com.example.deliveryapp.auth.di

import com.example.deliveryapp.auth.data.matcher.EmailPatternValidator
import com.example.deliveryapp.auth.data.remote.AuthApiService
import com.example.deliveryapp.auth.data.repository.AuthRepositoryImp
import com.example.deliveryapp.auth.domain.validation.PatternValidator
import com.example.deliveryapp.auth.domain.repository.AuthRepository
import com.example.deliveryapp.auth.domain.usecases.AuthUseCases
import com.example.deliveryapp.auth.domain.usecases.LoginWithEmailAndPasswordUseCase
import com.example.deliveryapp.auth.domain.validation.UserDataValidator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    fun providePatternValidator(): PatternValidator {
        return EmailPatternValidator()
    }

    @Provides
    fun provideAuthRepository(
        api: AuthApiService
    ): AuthRepository{
        return AuthRepositoryImp(api = api)
    }

    @Provides
    @Singleton
    fun provideUserDataValidator(
        patternValidator: PatternValidator
    ): UserDataValidator{
        return UserDataValidator(patternValidator)
    }

    @Provides
    @Singleton
    fun provideAuthUseCases(
        repository: AuthRepository
    ): AuthUseCases{
        return AuthUseCases(
            login = LoginWithEmailAndPasswordUseCase(repository)
        )
    }
}