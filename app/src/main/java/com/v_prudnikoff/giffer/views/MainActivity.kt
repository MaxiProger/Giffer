package com.v_prudnikoff.giffer.views

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import com.v_prudnikoff.giffer.R
import com.v_prudnikoff.giffer.interfaces.DataInteface
import com.v_prudnikoff.giffer.models.GifModel
import com.v_prudnikoff.giffer.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    var mainViewModel: MainViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainViewModel = MainViewModel(this)
        toolbar.title = getString(R.string.app_name)
        setSupportActionBar(toolbar)
        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener(this)

        val transaction = fragmentManager.beginTransaction()
        transaction.add(R.id.fragment_container, GridFragment.newInstance(), "grid")
        transaction.addToBackStack(null)
        transaction.commit()
        mainViewModel!!.onCreate()
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.mainSearch_item).actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                mainViewModel!!.loadQueryGifs(query)
                toolbar.title = "Search: " + query
                searchView.clearFocus()
                return true
            }
            override fun onQueryTextChange(newText: String): Boolean {
                return true
            }
        })
        return true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_trending -> {
                val transaction = fragmentManager.beginTransaction()
                transaction.replace(R.id.fragment_container, GridFragment.newInstance(), "grid")
                transaction.addToBackStack(null)
                transaction.commit()
                mainViewModel!!.onCreate()
            }
            R.id.nav_about -> {
                val transaction = fragmentManager.beginTransaction()
                transaction.replace(R.id.fragment_container, InfoFragment.newInstance(), "info")
                transaction.addToBackStack(null)
                transaction.commit()
            }
            R.id.nav_share -> {
                startShareIntent()
            }
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    fun setDataChanged(data: Array<GifModel>) {
        val currentFragment = fragmentManager.findFragmentByTag("grid")
        if (currentFragment != null && currentFragment.isVisible) {
            (currentFragment as DataInteface).setDataChanged(data)
        }
    }

    private fun startShareIntent() {
        val sharingIntent = Intent(Intent.ACTION_SEND)
        sharingIntent.type = "text/plain"
        val shareBody = "Enjoy Giffer!"
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Giffer")
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody)
        startActivity(Intent.createChooser(sharingIntent, "Share via"))
    }

}
