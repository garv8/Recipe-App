package com.garv.bookhub


import android.app.Activity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.FrameLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView



class MainActivity : AppCompatActivity() {
    lateinit var drawerLayout:DrawerLayout
    lateinit var coordinatorLayout:CoordinatorLayout
    lateinit var toolbar:Toolbar
    lateinit var frameLayout:FrameLayout

    lateinit var navigationView: NavigationView
    var pmi:MenuItem?=null
val c=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        drawerLayout = findViewById(R.id.drawerLayout)
        coordinatorLayout = findViewById(R.id.coordinatorLayout)
        toolbar = findViewById(R.id.toolbar)
        frameLayout = findViewById(R.id.frame)
        navigationView = findViewById(R.id.navigationView)

        setUpToolbar()
        opendashboard()

        val actionBarDrawerToggle=ActionBarDrawerToggle(this@MainActivity, drawerLayout, R.string.open_drawer, R.string.closed_drawer)
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        navigationView.setNavigationItemSelectedListener {
            if(pmi!=null)
            {
                pmi?.isChecked=false
            }
            it.isCheckable=true
            it.isChecked=true
            pmi=it
            when (it.itemId) {
                R.id.dashboard -> {
                    opendashboard()
                    drawerLayout.closeDrawers()
                }
                R.id.favorites -> {
                    supportFragmentManager.beginTransaction().replace(R.id.frame, FavoritesFragment()).commit()
                    drawerLayout.closeDrawers()
                    supportActionBar?.title="Favorites"
                }
                R.id.profile -> {
                    supportFragmentManager.beginTransaction().replace(R.id.frame, BlankFragment()).commit()
                    drawerLayout.closeDrawers()
                    supportActionBar?.title="Profile"
                }
                R.id.aboutApp -> {
                    supportFragmentManager.beginTransaction().replace(R.id.frame, AboutAppFragment()).commit()
                    drawerLayout.closeDrawers()
                    supportActionBar?.title="About App"
                }
            }
            return@setNavigationItemSelectedListener true
        }
    }
        fun setUpToolbar(){
            setSupportActionBar(toolbar)
            supportActionBar?.title="Book Hub"
            supportActionBar?.setHomeButtonEnabled(true)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id=item.itemId
        if(id==android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        return super.onOptionsItemSelected(item)
    }


    fun opendashboard()
    {

        supportActionBar?.title="Dashboard"
        supportFragmentManager.beginTransaction().replace(R.id.frame, DashboardFragment()).commit()
        navigationView.setCheckedItem(R.id.dashboard)
    }



    override fun onBackPressed() {
        val frag=supportFragmentManager.findFragmentById(R.id.frame)
        when(frag){
            !is DashboardFragment->opendashboard()




        else->{
            super.onBackPressed()

            ActivityCompat.finishAffinity(this as Activity);
        }



        }

    }




}