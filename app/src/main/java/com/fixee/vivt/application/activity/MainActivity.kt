package com.fixee.vivt.application.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.fixee.vivt.R
import com.fixee.vivt.application.fragments.BrsFragment
import com.fixee.vivt.application.helpers.BottomNavController
import com.fixee.vivt.application.helpers.setUpNavigation
import com.fixee.vivt.data.storage.entity.Token
import com.fixee.vivt.di.App
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), BottomNavController.NavGraphProvider {

    @Inject
    lateinit var token: Token

    private val navController by lazy(LazyThreadSafetyMode.NONE) {
        Navigation.findNavController(this, R.id.container)
    }

    private val bottomNavController by lazy(LazyThreadSafetyMode.NONE) {
        BottomNavController(this, R.id.container, R.id.news_graph)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        App.getMainComponent().inject(this)
        checkAuth()
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavController.setNavGraphProvider(this)
        bottomNavigationView.setUpNavigation(bottomNavController)

        if (savedInstanceState == null) bottomNavController.onNavigationItemSelected()
    }

    override fun onAttachFragment(fragment: Fragment) {
        super.onAttachFragment(fragment)

        // if token wrong logout
        (fragment as? BrsFragment).let {
            it?.logout = {
                logout()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        App.clearMainComponent()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.settings -> {
                toSettings()
                true
            }

            R.id.logout -> {
                logout()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun checkAuth() {
        when (token.token) {
            "" -> toLogin()
        }
    }

    private fun toSettings() {
        startActivity(Intent(this, SettingsActivity::class.java))
    }

    private fun logout() {
        App.getComponent().provideRoomAppDatabase().tokenDao().deleteToken(token)
        toLogin()
    }

    private fun toLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    override fun getNavGraphId(itemId: Int) = when (itemId) {
        R.id.news_graph -> R.navigation.news_graph
        R.id.schedule_graph -> R.navigation.schedule_graph
        R.id.brs_graph -> R.navigation.brs_graph
        else -> R.navigation.news_graph
    }

    override fun onSupportNavigateUp(): Boolean = navController.navigateUp()

    override fun onBackPressed() = bottomNavController.onBackPressed()

}