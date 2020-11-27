package com.example.maintanancebuddy
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.navigation.NavArgument
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.maintanancebuddy.Models.User
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.mainactivity.*
import kotlin.properties.Delegates

class MainActivity: AppCompatActivity(){

     var isadmin by Delegates.notNull<Int>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mainactivity)
        verifyuserLogin()

        //supportActionBar?.setDisplayHomeAsUpEnabled(true)
        openbottomnav()

    }


    private fun hideBottomNav() {
        nav_view.visibility = View.GONE
    }

    private fun showBottomNav() {
        nav_view.visibility = View.VISIBLE
    }
    private fun verifyuserLogin()
    {
        val uid = FirebaseAuth.getInstance().uid
        if (uid == null)
        {
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }

    private fun openbottomnav()
    {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        findViewById<BottomNavigationView>(R.id.nav_view)
            .setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _->
            when(destination.id)
            {
                R.id.chatLog-> {
                    hideBottomNav()
                    //supportActionBar?.setDisplayHomeAsUpEnabled(true)
                }
                R.id.repair_Resident_History_details->
                {
                    hideBottomNav()
                }
                else -> {
                    showBottomNav()
                    supportActionBar?.setDisplayHomeAsUpEnabled(false)
                }
            }
        }
    }



}


