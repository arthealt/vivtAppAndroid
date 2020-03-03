package com.fixee.vivt.application.activity

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.fixee.vivt.R
import com.fixee.vivt.application.adapters.NotificationsAdapter
import com.fixee.vivt.application.helpers.Util
import com.fixee.vivt.application.intent.StateNotifications
import com.fixee.vivt.application.viewmodels.NotificationsViewModel
import com.fixee.vivt.application.viewmodels.NotificationsViewModelFactory
import com.fixee.vivt.data.remote.models.Notification
import com.fixee.vivt.di.App
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_notification.*
import javax.inject.Inject

class NotificationActivity : AppCompatActivity() {

    @Inject
    lateinit var util: Util
    @Inject
    lateinit var viewModelFactory: NotificationsViewModelFactory
    private lateinit var viewModel: NotificationsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)
        App.getMainComponent().inject(this)

        // for button back
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        viewModel = ViewModelProviders.of(this, viewModelFactory)[NotificationsViewModel::class.java]
        lifecycle.addObserver(viewModel)

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)

        //todo update swipe
        viewModel.state.observe(this, Observer<StateNotifications> { value ->
            when (value) {

                is StateNotifications.LoadingState -> {
                    recyclerView.visibility = View.GONE
                    errorText.visibility = View.GONE
                    progressBar.visibility = View.VISIBLE
                }

                is StateNotifications.SuccessLoad -> {
                    progressBar.visibility = View.GONE
                    errorText.visibility = View.GONE
                    recyclerView.visibility = View.VISIBLE

                    updateRecyclerView(value.notifications)
                }

                is StateNotifications.ErrorLoad -> {
                    progressBar.visibility = View.GONE
                    recyclerView.visibility = View.GONE
                    errorText.visibility = View.GONE

                    btnRefreshSetOnClick()

                    when (value.error.code) {
                        298 -> snackError(getString(R.string.error_connection)) // No internet
                        299 -> snackError(getString(R.string.error_unexpected)) // Unexpected error
                        300 -> logout() // Wrong token, logout
                        301 -> { // No yet data todo text
                            errorText.text = "No data"
                            errorText.visibility = View.VISIBLE
                        }
                        else -> { // Other error from server
                            errorText.text = value.error.name
                            errorText.visibility = View.VISIBLE
                        }
                    }
                }
            }
        })
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

    private fun btnRefreshSetOnClick() {
        btnRefresh.setOnClickListener {
            //todo update
        }
    }

    private fun logout() {
        viewModel.logout()
        toLogin()
    }

    private fun toLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    private fun updateRecyclerView(notifications: ArrayList<Notification>) {
        recyclerView.adapter = NotificationsAdapter(notifications)
        recyclerView.adapter?.notifyDataSetChanged()
    }

    private fun snackError(error: String) {
        Snackbar.make(snackBar, error, Snackbar.LENGTH_LONG).show()
    }
}