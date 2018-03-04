package com.v_prudnikoff.giffer.views

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.v_prudnikoff.giffer.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toolbar.title = getString(R.string.app_name)
        setSupportActionBar(toolbar)
        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener(this)
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
        return true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_about -> {
                val transaction = fragmentManager.beginTransaction()
                transaction.add(R.id.fragment_container, InfoFragment.newInstance(), "info")
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

    private fun startShareIntent() {
        val sharingIntent = Intent(Intent.ACTION_SEND)
        sharingIntent.type = "text/plain"
        val shareBody = "Enjoy Giffer!"
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Giffer")
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody)
        startActivity(Intent.createChooser(sharingIntent, "Share via"))
    }
}
