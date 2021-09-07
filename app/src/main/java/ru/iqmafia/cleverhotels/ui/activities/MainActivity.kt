package ru.iqmafia.cleverhotels.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch
import ru.iqmafia.cleverhotels.R
import ru.iqmafia.cleverhotels.utils.ACTIVITY
import ru.iqmafia.cleverhotels.utils.MY_IO_SCOPE
import ru.iqmafia.cleverhotels.utils.ROOM_REPOSITORY

class MainActivity : AppCompatActivity() {

    lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ACTIVITY = this
        initBottomNav()

    }


    private fun initBottomNav() {
        val botNav: BottomNavigationView = findViewById(R.id.main_bottom_nav)
        navController = findNavController(R.id.main_container)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.listFragment,
                R.id.infoFragment,
                R.id.picFragment,
            )
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        botNav.setupWithNavController(navController)
//        botNav.selectedItemId = R.id.listFragment
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}