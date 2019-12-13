package com.fixee.vivt.application.fragments

import android.os.Bundle
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
import com.fixee.vivt.application.adapters.BrsAdapter
import com.fixee.vivt.application.helpers.Util
import com.fixee.vivt.application.intent.StateBrs
import com.fixee.vivt.application.viewmodels.BrsViewModel
import com.fixee.vivt.application.viewmodels.BrsViewModelFactory
import com.fixee.vivt.data.remote.models.Brs
import com.fixee.vivt.data.remote.models.Error
import com.fixee.vivt.di.App
import com.google.android.material.chip.ChipGroup
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_brs.*
import javax.inject.Inject


class BrsFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: BrsViewModelFactory
    @Inject
    lateinit var util: Util
    private lateinit var viewModel: BrsViewModel
    // for wrong token
    var logout: (() -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        App.getMainComponent().inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_brs, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        (activity as MainActivity).supportActionBar?.title = getString(R.string.brs)

        viewModel = ViewModelProviders.of(this, viewModelFactory)[BrsViewModel::class.java]
        lifecycle.addObserver(viewModel)

        //recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)
        //recyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        chips.setOnCheckedChangeListener { chipGroup: ChipGroup, _: Int ->

            var semester = 0

            when (chipGroup.checkedChipId) {
                R.id.chip1 -> semester = 1
                R.id.chip2 -> semester = 2
                R.id.chip3 -> semester = 3
                R.id.chip4 -> semester = 4
                R.id.chip5 -> semester = 5
                R.id.chip6 -> semester = 6
                R.id.chip7 -> semester = 7
                R.id.chip8 -> semester = 8
                R.id.chip9 -> semester = 9
                R.id.chip10 -> semester = 10
                R.id.chip11 -> semester = 11
                R.id.chip12 -> semester = 12
            }

            if (semester != 0) {
                if (util.isInternetConnection()) {
                    viewModel.getBrs(semester)
                } else {
                    viewModel.state.apply { value = StateBrs.ErrorLoad(Error(298, ""), semester) }
                }
            }
        }

        viewModel.state.observe(this, Observer<StateBrs> { value ->
            when(value) {

                is StateBrs.LoadingState -> {
                    recyclerView.visibility = GONE
                    errorText.visibility = GONE
                    progressBar.visibility = VISIBLE

                    if (value.semester == 1) {
                        chip1.isChecked = true
                        chip1.isClickable = false
                    }

                    updateChips(false, value.semester)
                }

                is StateBrs.SuccessLoad -> {
                    progressBar.visibility = GONE
                    errorText.visibility = GONE
                    recyclerView.visibility = VISIBLE

                    updateRecyclerView(value.brs)

                    updateChips(true, value.semester)
                }

                is StateBrs.ErrorLoad -> {
                    progressBar.visibility = GONE
                    recyclerView.visibility = GONE
                    errorText.visibility = GONE

                    updateChips(true, value.semester)

                    when (value.error.code) {
                        298 -> snackError(getString(R.string.error_connection)) // No internet
                        299 -> snackError(getString(R.string.error_unexpected)) // Unexpected error
                        300 -> logout?.invoke() // Wrong token, logout
                        301 -> { // No yet data for semester
                            errorText.text = getString(R.string.error_not_yet_data_semester)
                            errorText.visibility = VISIBLE
                        }
                        else -> { // Other error from server
                            errorText.text = value.error.name
                            errorText.visibility = VISIBLE
                        }
                    }
                }
            }
        })
    }

    private fun updateChips(action: Boolean, semester: Int) {
        if (semester != 1) chip1.isClickable = action else if (!action) chip1.isClickable = false
        if (semester != 2) chip2.isClickable = action else if (!action) chip2.isClickable = false
        if (semester != 3) chip3.isClickable = action else if (!action) chip3.isClickable = false
        if (semester != 4) chip4.isClickable = action else if (!action) chip4.isClickable = false
        if (semester != 5) chip5.isClickable = action else if (!action) chip5.isClickable = false
        if (semester != 6) chip6.isClickable = action else if (!action) chip6.isClickable = false
        if (semester != 7) chip7.isClickable = action else if (!action) chip7.isClickable = false
        if (semester != 8) chip8.isClickable = action else if (!action) chip8.isClickable = false
        if (semester != 9) chip9.isClickable = action else if (!action) chip9.isClickable = false
        if (semester != 10) chip10.isClickable = action else if (!action) chip10.isClickable = false
        if (semester != 11) chip11.isClickable = action else if (!action) chip11.isClickable = false
        if (semester != 12) chip12.isClickable = action else if (!action) chip12.isClickable = false
    }

    private fun updateRecyclerView(brsList: ArrayList<Brs>) {
        recyclerView.adapter = BrsAdapter(brsList)
        recyclerView.adapter!!.notifyDataSetChanged()
    }

    private fun snackError(error: String) {
        Snackbar.make(snackBar, error, Snackbar.LENGTH_LONG).show()
    }
}