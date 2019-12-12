@file:Suppress("DEPRECATION")
package com.fixee.vivt.application.activity

import android.animation.LayoutTransition
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.fixee.vivt.R
import com.fixee.vivt.application.helpers.StateLogin
import com.fixee.vivt.application.helpers.Util
import com.fixee.vivt.application.helpers.md5
import com.fixee.vivt.application.viewmodels.LoginViewModel
import com.fixee.vivt.application.viewmodels.LoginViewModelFactory
import com.fixee.vivt.di.App
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject

class LoginActivity : AppCompatActivity() {

    @Inject
    lateinit var loginViewModelFactory: LoginViewModelFactory
    @Inject
    lateinit var util: Util
    private lateinit var viewModel: LoginViewModel
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // hide actionBar
        supportActionBar?.hide()
        // for animation scroll y
        relativeLayout.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
        titleBox.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
        // background color
        window.setBackgroundDrawable(getDrawable(R.drawable.bg_login))
        // init progress dialog
        progressDialog = ProgressDialog(this, R.style.ProgressDialog)
        progressDialog.setMessage(getString(R.string.authorization))
        progressDialog.setCancelable(false)

        App.getLoginComponent().inject(this)
        viewModel = ViewModelProviders.of(this, loginViewModelFactory)[LoginViewModel::class.java]
        lifecycle.addObserver(viewModel)

        btnAuth.setOnClickListener {

            if (util.isInternetConnection()) {
                if (validateFields()) {
                    val email = editEmail.text.toString().trim()
                    val password = editPassword.text.toString().md5()
                    val fcmToken = getSharedPreferences("main", Context.MODE_PRIVATE).getString("fcm_token", "")

                    btnAuth.isEnabled = false
                    hideKeyboard()
                    viewModel.auth(email, password, fcmToken!!)
                }
            } else {
                hideKeyboard()
                snackError(getString(R.string.error_connection))
            }
        }

        viewModel.state.observe(this, Observer<StateLogin> { value ->
            when (value) {

                is StateLogin.LoadingState -> {
                    progressDialog(true)
                }

                is StateLogin.SuccessLogin -> {
                    progressDialog(false)
                    toMain()
                }

                is StateLogin.ErrorLogin -> {
                    progressDialog(false)
                    btnAuth.isEnabled = true
                    snackError(value.error)
                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        App.clearLoginComponent()
    }

    private fun progressDialog(show: Boolean) {
        if (show) progressDialog.show() else progressDialog.hide()
    }

    private fun toMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun snackError(error: String) {
        Snackbar.make(snackBar, error, Snackbar.LENGTH_LONG).show()
    }

    private fun validateFields(): Boolean {
        var valid = true

        val email = editEmail.text.toString().trim()
        val password = editPassword.text.toString()

        if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editEmail.error = null
        } else {
            editEmail.error =  if (email.isEmpty()) getString(R.string.fill_field) else getString(R.string.email_err)
            valid = false
        }

        if (password.length > 5) {
            editPassword.error = null
        } else {
            editPassword.error = if (password.isEmpty()) getString(R.string.fill_field) else getString(R.string.password_short)
            valid = false
        }

        return valid
    }

    private fun AppCompatActivity.hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        } else {
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        }
    }

}