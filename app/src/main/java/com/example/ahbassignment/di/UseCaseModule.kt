package com.example.ahbassignment.di

import com.example.ahbassignment.repository.FirebaseRemoteConfigRepository
import com.example.ahbassignment.usecase.RemoteConfigUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

/**
 * Created by Shaheer cs on 19/02/2022.
 */
@Module
@InstallIn(ActivityRetainedComponent::class)
object UseCaseModule {
    @Provides
    fun remoteConfigUseCaseProvider(repository: FirebaseRemoteConfigRepository) =
        RemoteConfigUseCase(repository)

    @Provides
    fun firebaseRemoteConfigRepositoryProvider() = FirebaseRemoteConfigRepository()
}