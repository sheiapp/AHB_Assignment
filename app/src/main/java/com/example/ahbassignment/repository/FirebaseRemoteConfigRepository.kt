package com.example.ahbassignment.repository

import com.example.ahbassignment.util.Resource
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * Created by Shaheer cs on 17/02/2022.
 */
class FirebaseRemoteConfigRepository {
    private val remoteConfig = Firebase.remoteConfig
    private val firebaseConfigKey = "AppMaintenance";


    suspend fun getFirebaseRemoteConfig(): Resource<String> = suspendCoroutine { continuation ->
        val configSettings = remoteConfigSettings {
            fetchTimeoutInSeconds = timeOutDelayInMillisecond
        }
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.fetchAndActivate()
            .addOnCompleteListener { task ->
                if (task.isSuccessful)
                    continuation.resume(Resource.Success(remoteConfig.getString(firebaseConfigKey)))
            }
            .addOnFailureListener { e ->
                continuation.resume(Resource.Error(message = e.localizedMessage))
            }
    }
    companion object{
        const val timeOutDelayInMillisecond: Long = 3 * 1000
    }
}

