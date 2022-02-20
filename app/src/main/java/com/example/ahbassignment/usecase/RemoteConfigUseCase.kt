package com.example.ahbassignment.usecase

import com.example.ahbassignment.data.Resource
import com.example.ahbassignment.data.model.GetFirebaseConfig
import com.example.ahbassignment.data.model.RemoteConfig
import com.example.ahbassignment.data.repository.FirebaseRemoteConfigRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.*
import javax.inject.Inject

/**
 * Created by Shaheer cs on 18/02/2022.
 */
class RemoteConfigUseCase @Inject constructor(
    private val repository: FirebaseRemoteConfigRepository
) {

    suspend operator fun invoke(): Flow<RemoteConfig?> {
        val type = object : TypeToken<GetFirebaseConfig>() {}.type
        var getFirebaseConfig: GetFirebaseConfig? = null
        var remoteConfig: RemoteConfig? = null
        when (repository.getFirebaseRemoteConfig()) {
            is Resource.Success -> {
                val firebaseRemoteConfig = repository.getFirebaseRemoteConfig().data
                getFirebaseConfig =
                    Gson().fromJson(firebaseRemoteConfig, type)
                if (getFirebaseConfig?.maintenanceStatus == true) {
                    remoteConfig = RemoteConfig(
                        getFirebaseConfig.maintenanceStatus,
                        if (Locale.getDefault().language.equals(
                                "en",
                                true
                            )
                        ) getFirebaseConfig.bannerTextEN else getFirebaseConfig.bannerTextARA
                    )
                }
                return flow { emit(remoteConfig) }
            }
            is Resource.Error -> {
                return flow { emit(RemoteConfig(false)) }
            }
        }
    }
}
