package com.example.maintanancebuddy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.maintanancebuddy.Models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import com.xwray.groupie.GroupAdapter

import kotlinx.android.synthetic.main.login.*

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private var postListener: ValueEventListener? = null
    private lateinit var ref: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)
        auth = Firebase.auth

        login_button_login.setOnClickListener()
        {
            SignIn()
        }
        forget_password_text_view.setOnClickListener()
        {
            Log.d("Login", "User forget password ")
            //val intent= Intent(this,Forget::class.java)
        }
        register_account.setOnClickListener()
        {
            Log.d("Login", "User new account clicked")
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

    }

    private fun SignIn() {
        val email = email_login.text.toString()
        val password = password_login.text.toString()

        if (email.isEmpty()) {
            Toast.makeText(this, "Please fill out email", Toast.LENGTH_SHORT).show()

        } else if (password.isEmpty()) {
            Toast.makeText(this, "Please fill out password", Toast.LENGTH_SHORT).show()

        } else {
            //FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this)
                {
                    if (it.isSuccessful) {
                        Log.d("Login", "Login Sucessful: ${it.result?.user?.uid}")
                        var uid = auth.uid ?: ""
                        checkUseraccesslevel(uid)
                    } else {
                        Log.d("Login", "Login Failed", it.exception)
                        Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show()
                    }
                }
                .addOnFailureListener(this) {
                    Log.d("Login", "Login Failed")
                    Toast.makeText(this, "Failed to log in: ${it.message}", Toast.LENGTH_SHORT)
                        .show()
                }
        }

    }

    private fun checkUseraccesslevel(uid: String) {
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val isadmin = snapshot.child("admin").getValue().toString().toInt()
                Log.d("Database", isadmin.toString())
                Log.d("Databse", snapshot.child("email").getValue().toString())
                openactivity(isadmin)

            }
            override fun onCancelled(error: DatabaseError){
                Log.d("Database", error.details)
            }
        })

        Log.d("ref", "$ref")
    }

    private fun openactivity(isadmin:Int)
    {

        val intent= Intent(this,MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.putExtra("isadmin",isadmin)
        //finish()
        startActivity(intent)

    }


}







