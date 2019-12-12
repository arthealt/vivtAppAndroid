package com.fixee.vivt.application.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.fixee.vivt.R


class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)

        supportFragmentManager.beginTransaction().replace(R.id.settings, SettingsFragment()).commit()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                setPreferencesFromResource(R.xml.root_preferences, rootKey)

                val btnOpenSettingsNotifications = findPreference<Preference>("btnOpenSettingsNotifications")

                btnOpenSettingsNotifications!!.setOnPreferenceClickListener {
                    val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
                        .putExtra(Settings.EXTRA_APP_PACKAGE, activity?.packageName)

                    startActivity(intent)
                    true
                }
            } else {
                setPreferencesFromResource(R.xml.with_notifications_preferences, rootKey)
            }
    }
}