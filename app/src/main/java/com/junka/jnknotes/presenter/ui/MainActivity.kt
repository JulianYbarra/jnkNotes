package com.junka.jnknotes.presenter.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.junka.jnknotes.R

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.activity_main)

        //val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
       // val appBarConfiguration = AppBarConfiguration(setOf(R.id.navigation_dashboard))

        //setupActionBarWithNavController(navController, appBarConfiguration)
    }
}