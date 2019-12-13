package com.fixee.vivt.application.activity

import android.os.Bundle
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import com.fixee.vivt.R
import com.fixee.vivt.application.helpers.Util
import com.fixee.vivt.application.intent.StateSettings
import com.fixee.vivt.application.viewmodels.SettingsViewModel
import com.fixee.vivt.application.viewmodels.SettingsViewModelFactory
import com.fixee.vivt.data.remote.models.Error
import com.fixee.vivt.di.App
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_settings.*
import javax.inject.Inject


class SettingsActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: SettingsViewModelFactory
    @Inject
    lateinit var util: Util
    private lateinit var viewModel: SettingsViewModel
    private val settingFragment = SettingsFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        App.getMainComponent().inject(this)

        supportFragmentManager.beginTransaction().replace(R.id.settings, settingFragment).commit()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        viewModel = ViewModelProviders.of(this, viewModelFactory)[SettingsViewModel::class.java]
        lifecycle.addObserver(viewModel)

        viewModel.state.observe(this, Observer<StateSettings> { value ->
            when(value) {

                is StateSettings.LoadingState -> {
                    progressBar.visibility = VISIBLE
                }

                is StateSettings.SuccessUpdate -> {
                    progressBar.visibility = GONE
                }

                is StateSettings.ErrorUpdate -> {
                    progressBar.visibility = GONE

                    when (value.error.code) {
                        298 -> snackError(getString(R.string.error_connection)) // No internet
                        299 -> snackError(getString(R.string.error_unexpected)) // Unexpected error
                        else -> { // Other error from server
                            snackError(value.error.name)
                        }
                    }
                }
            }
        })
    }

    override fun onAttachFragment(fragment: Fragment) {
        super.onAttachFragment(fragment)

        // callback change switch
        (fragment as? SettingsFragment).let {
            it?.changeSwitch = { item: Int, action: Boolean ->
                if (util.isInternetConnection()) {
                    viewModel.updatePush(item, action)
                    true
                } else {
                    viewModel.state.apply { value = StateSettings.ErrorUpdate(Error(298, "")) }
                    false
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        App.clearMainComponent()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun snackError(error: String) {
        Snackbar.make(snackBar, error, Snackbar.LENGTH_LONG).show()
    }

    class SettingsFragment : PreferenceFragmentCompat() {

        var changeSwitch: ((item: Int, action: Boolean) -> Boolean)? = null
        private val switches: ArrayList<SwitchPreferenceCompat> = ArrayList()

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)

            for (i in 1..5) {
                switches.add(findPreference(i.toString())!!)
                switches[i - 1].setOnPreferenceChangeListener { _, value ->
                    changeSwitch?.invoke(i, Boolean == value)!!
                }
            }
        }
    }
}