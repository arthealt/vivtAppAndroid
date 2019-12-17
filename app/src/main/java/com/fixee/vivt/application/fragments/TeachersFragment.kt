package com.fixee.vivt.application.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.fixee.vivt.R
import com.fixee.vivt.application.activity.MainActivity
import com.fixee.vivt.application.adapters.TeachersAdapter
import com.fixee.vivt.application.helpers.Util
import com.fixee.vivt.application.intent.StateTeachers
import com.fixee.vivt.application.viewmodels.TeacherViewModelFactory
import com.fixee.vivt.application.viewmodels.TeachersViewModel
import com.fixee.vivt.data.remote.models.Teacher
import com.fixee.vivt.di.App
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_teachers.*
import javax.inject.Inject


class TeachersFragment : Fragment() {

    @Inject
    lateinit var util: Util
    @Inject
    lateinit var viewModelFactory: TeacherViewModelFactory
    private lateinit var viewModel: TeachersViewModel
    // for wrong token
    var logout: (() -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.getMainComponent().inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_teachers, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        (activity as MainActivity).supportActionBar?.title = getString(R.string.teachers)

        viewModel = ViewModelProviders.of(this, viewModelFactory)[TeachersViewModel::class.java]
        lifecycle.addObserver(viewModel)

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)

        searchEditText.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (recyclerView.adapter != null) {
                    (recyclerView.adapter as TeachersAdapter).filter?.filter(s)
                }
            }
        })

        btnRefresh.setOnClickListener {
            viewModel.getTeachers()
        }

        viewModel.state.observe(this, Observer<StateTeachers> { value ->
            when (value) {

                is StateTeachers.LoadingState -> {
                    recyclerView.visibility = GONE
                    errorText.visibility = GONE
                    btnRefresh.visibility = GONE
                    searchEditText.visibility = GONE
                    progressBar.visibility = VISIBLE
                }

                is StateTeachers.SuccessLoad -> {
                    progressBar.visibility = GONE
                    errorText.visibility = GONE
                    btnRefresh.visibility = GONE
                    searchEditText.visibility = VISIBLE
                    recyclerView.visibility = VISIBLE

                    updateRecyclerView(value.teachers)
                }

                is StateTeachers.ErrorLoad -> {
                    progressBar.visibility = GONE
                    recyclerView.visibility = GONE
                    errorText.visibility = GONE
                    searchEditText.visibility = GONE
                    btnRefresh.visibility = VISIBLE

                    when (value.error.code) {
                        298 -> snackError(getString(R.string.error_connection)) // No internet
                        299 -> snackError(getString(R.string.error_unexpected)) // Unexpected error
                        300 -> logout?.invoke() // Wrong token, logout
                        else -> { // Other error from server
                            errorText.text = value.error.name
                            errorText.visibility = VISIBLE
                        }
                    }
                }

            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        App.clearMainComponent()
    }

    private fun updateRecyclerView(teacherList: ArrayList<Teacher>) {
        recyclerView.adapter = TeachersAdapter(teacherList)
        recyclerView.adapter!!.notifyDataSetChanged()

        if (searchEditText.text.toString().isNotEmpty()) {
            searchEditText.text.clear()
        }
    }

    private fun snackError(error: String) {
        Snackbar.make(snackBar, error, Snackbar.LENGTH_LONG).show()
    }
}