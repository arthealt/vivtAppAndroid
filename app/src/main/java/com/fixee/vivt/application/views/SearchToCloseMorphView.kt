package com.fixee.vivt.application.views

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import com.fixee.vivt.R

class SearchToCloseMorphView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : AppCompatImageView(context, attrs, defStyleAttr) {

    private var searchToClose: AnimatedVectorDrawableCompat? = null
    private var closeToSearch: AnimatedVectorDrawableCompat? = null
    private var showingSearch = false

    init {
        showingSearch = true
        searchToClose = AnimatedVectorDrawableCompat.create(context, R.drawable.avd_search_to_close)
        closeToSearch = AnimatedVectorDrawableCompat.create(context, R.drawable.avd_close_to_search)
        setImageDrawable(searchToClose)
    }

    fun showSearch() {
        if (!showingSearch) {
            morph()
        }
    }

    fun showClose() {
        if (showingSearch) {
            morph()
        }
    }

    fun getShowSearch() = showingSearch

    private fun morph() {
        val drawable = if (showingSearch) searchToClose else closeToSearch
        setImageDrawable(drawable)
        drawable?.start()
        showingSearch = !showingSearch
    }
}