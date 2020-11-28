package com.example.maintanancebuddy

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat.startActivity
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

            val builder = AlertDialog.Builder(this)
            builder.setTitle("Forgot Password")
            val view = layoutInflater.inflate(R.layout.dialog_forgot_password,null)
            val username= view.findViewById<EditText>(R.id.et_username)
            builder.setView(view)
            builder.setPositiveButton("Reset", DialogInterface.OnClickListener{ _, _->
                forgotPassword(username)
            })
            builder.setNegativeButton("Close",DialogInterface.OnClickListener{_,_->})
            builder.show()
        }

            Log.d("Login", "User forget password ")
            //val intent= Intent(this,Forget::class.java)

        register_account.setOnClickListener()
        {
            Log.d("Login", "User new account clicked")
            val intent = Intent(this, RegisterActivity::class.java) //
            startActivity(intent) //
        }

    }
    private fun forgotPassword(username:EditText) {
        if(username.text.toString().isEmpty()) {
            return
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(username.text.toString()).matches()){
            return
        }
        Firebase.auth.sendPasswordResetEmail(username.text.toString())
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Email sent.",Toast.LENGTH_SHORT).show()

                }
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
                        /*
                        var uid = auth.uid ?: ""

                        val intent= Intent(this,MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)

                         */
                        checkuseraccesslevel()
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
    private fun checkuseraccesslevel()
    {
        val uid = FirebaseAuth.getInstance().uid
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val isadmin = snapshot.child("admin").getValue().toString().toInt()
                val user= snapshot.getValue(User::class.java)
                Log.d("MainActivity", "isadmin: $isadmin")
                if (user != null) {
                    savedata(user)
                    openactivity()
                }


            }
            override fun onCancelled(error: DatabaseError){
                Log.d("Database", error.details)
            }
        })


    }
    private fun savedata(user: User)
    {
        val sharedPreference =  getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        var editor = sharedPreference.edit()
        editor.putInt("isadmin",user.isAdmin)
        editor.putString("username", user.fname +" "+ user.lname)
        editor.putString("email",user.email)
        editor.putString("aptno",user.aptno)
        editor.putString("uid",user.uid)
        editor.putString("cellphone",user.cellphone)
        editor.commit()
    }
    private fun openactivity()
    {
        val intent= Intent(this,MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

}







