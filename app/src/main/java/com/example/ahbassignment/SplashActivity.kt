package com.example.ahbassignment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.ahbassignment.model.GetFirebaseConfig
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.google.gson.Gson
import com.google.gson.JsonParseException
import com.google.gson.reflect.TypeToken
import java.util.*

class SplashActivity : AppCompatActivity() {
    private lateinit var remoteConfig: FirebaseRemoteConfig
    private val firebaseConfigKey = "AppMaintenance";
    private val delayInMillisecond: Long = 3 * 1000
    private val intervalInMilliseconds: Long = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        handleSplashScreenTiming()
    }

    private fun handleFirebaseRemoteConfig() {
        remoteConfig = Firebase.remoteConfig
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = intervalInMilliseconds
        }
        remoteConfig.setConfigSettingsAsync(configSettings)

        remoteConfig.fetchAndActivate()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val type = object : TypeToken<GetFirebaseConfig>() {}.type
                    var getFirebaseConfig: GetFirebaseConfig? = null
                    try {
                        getFirebaseConfig =
                            Gson().fromJson(remoteConfig.getString(firebaseConfigKey), type)
                    } catch (e: JsonParseException) {
                        Log.e(this.javaClass.simpleName, e.message.toString())
                    }
                    if (getFirebaseConfig?.maintenanceStatus == true) {
                        showCustomBanner(
                            if (Locale.getDefault().language.equals(
                                    "en",
                                    true
                                )
                            ) getFirebaseConfig.bannerTextEN else getFirebaseConfig.bannerTextARA
                        )
                    } else {
                        handleSplashScreenTiming()
                    }

                }
            }
    }

    private fun handleSplashScreenTiming() {
        Handler(Looper.getMainLooper()).postDelayed({
            handleFirebaseRemoteConfig()
            //fire next activity
        }, delayInMillisecond)
    }

    private fun showCustomBanner(text: String) {
        findViewById<LinearLayout>(R.id.banner).visibility = View.VISIBLE
        findViewById<TextView>(R.id.reason).text = text
    }

}