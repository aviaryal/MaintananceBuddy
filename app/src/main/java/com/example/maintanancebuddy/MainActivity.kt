package com.example.maintanancebuddy
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.mainactivity.*

class MainActivity: AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mainactivity)

        verifyuserLogin()
        /*
        val isadmin= intent.getIntExtra("isadmin",0)
        Log.d("MainActivity", isadmin.toString())
        val navView:BottomNavigationView=findViewById(R.id.nav_view)
        val navController=findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.nav_home, R.id.nav_repair, R.id.nav_chat,R.id.nav_profile))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

         */
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        findViewById<BottomNavigationView>(R.id.nav_view)
            .setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.chatLog -> hideBottomNav()
                else -> showBottomNav()
            }
        }
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
            val intent = Intent(this, RegisterActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }

}


