package com.example.myapplication.app.application.activity

import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.myapplication.R
import com.example.myapplication.app.application.helpers.State
import com.example.myapplication.app.application.helpers.md5
import com.example.myapplication.app.application.viewmodels.MainViewModel
import com.example.myapplication.app.application.viewmodels.MainViewModelFactory
import com.example.myapplication.app.di.App
import com.example.myapplication.app.domain.implementations.TestRepositoryImpl
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var repository: TestRepositoryImpl
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        App.getComponent().inject(this@MainActivity)
        viewModel = ViewModelProviders.of(this, MainViewModelFactory(repository)).get(MainViewModel::class.java)

        //val people = People(id = null, name = "Ivan", role = "kek")

        //viewModel.uploadLocal(people)
        //viewModel.fetchLocal()




        btnAuth.setOnClickListener {

            val email = editEmail.text.toString()
            val password = editPassword.text.toString().md5()

             if (validateFields()) viewModel.auth(email, password)

            //Toast.makeText(this@MainActivity, "$email, $password", LENGTH_LONG).show()
            //viewModel.auth(email, password)
        }

        viewModel.state.observe(this@MainActivity, Observer<State> { value ->
            when (value) {
                is State.LoadedState<*> -> {
                    value.data.forEach{
                        println(it)
                    }
                }

                is State.SuccessLogin -> {
                    Toast.makeText(this@MainActivity, value.auth.token, LENGTH_LONG).show()
                }

                is State.ErrorLogin -> {
                    Toast.makeText(this@MainActivity, value.error, LENGTH_LONG).show()
                }
            }
        })
    }

    fun validateFields(): Boolean {

        var valid = true

        if (Patterns.EMAIL_ADDRESS.matcher(editEmail.text.toString()).matches()) {
            editEmail.error = null
        } else {
            valid = false
            editEmail.error = "Email введен не верно"
        }

        if (editPassword.text.toString().length > 5) {
            editPassword.error = null
        } else {
            valid = false
            editPassword.error = "Пароль слишком короткий"
        }

        return valid
    }

}