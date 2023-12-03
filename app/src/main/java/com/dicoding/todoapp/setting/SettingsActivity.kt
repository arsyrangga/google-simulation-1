package com.dicoding.todoapp.setting

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.WorkManager
import com.dicoding.todoapp.R
import androidx.work.NetworkType
import androidx.work.*
import com.dicoding.todoapp.notification.NotificationWorker
import java.util.concurrent.TimeUnit

class SettingsActivity : AppCompatActivity() {
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            showToast("Notifications permission granted")
        } else {
            showToast("Notifications will not show without permission")
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(R.id.settings, SettingsFragment())
                .commit()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)

    }

    private fun startPeriodicTask() {

    }


    class SettingsFragment : PreferenceFragmentCompat() {
        private lateinit var periodicWorkRequest: PeriodicWorkRequest


        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)


            val prefNotification =
                findPreference<SwitchPreference>(getString(R.string.pref_key_notify))

            prefNotification?.setOnPreferenceChangeListener { preference, newValue ->
                val channelName = getString(R.string.notify_channel_name)

                //TODO 13 : Schedule and cancel daily reminder using WorkManager with data channelName
                val data = Data.Builder()
                    .putString("channelName", channelName)
                    .build()
                periodicWorkRequest =
                    PeriodicWorkRequest.Builder(NotificationWorker::class.java, 24, TimeUnit.HOURS)
                        .setInputData(data)
                        .build()

                if (newValue is Boolean) {
                    if (newValue) {
                        scheduleDailyReminder()
                    } else {
                        cancelDailyReminder()
                    }
                }
                true
            }
        }


        private fun scheduleDailyReminder() {
            WorkManager.getInstance().enqueue(periodicWorkRequest)
        }


        private fun cancelDailyReminder() {
            WorkManager.getInstance().cancelWorkById(periodicWorkRequest.id)
        }


    }
}

