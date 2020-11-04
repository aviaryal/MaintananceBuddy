package com.example.maintanancebuddy

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.maintanancebuddy.Models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.register.*
import java.util.regex.Matcher
import java.util.regex.Pattern


class RegisterActivity : AppCompatActivity(){

    private lateinit var auth: FirebaseAuth
    private lateinit var fstore: FirebaseFirestore;

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)
        auth= FirebaseAuth.getInstance()
        fstore= FirebaseFirestore.getInstance()
        register_button_register.setOnClickListener()
        {
            createAccount()
        }
    }
    private fun createAccount()
    {
        val firstname= Fname_register.text.toString()
        val lastname= LName_register.text.toString()
        val aptno= Aptno_register.text.toString()   //need to work its interger
        val email= email_edittext_register.text.toString()
        val password=password_edittext_register.text.toString()
        val password_check = password_check_register.text.toString()

        if (email.isEmpty() || password.isEmpty() || firstname.isEmpty() || lastname.isEmpty() || aptno.isEmpty() || password_check.isEmpty())
        {
            Toast.makeText(this, "Empty Field", Toast.LENGTH_SHORT).show()
            return
        }
        if(password != password_check)
        {
            Toast.makeText(this, "Password didn't match", Toast.LENGTH_SHORT).show()
            return
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            Toast.makeText(this, "Not valid email", Toast.LENGTH_SHORT).show()
            return
        }
        /*
        if(password.length<8 && !isValidPassowrd(password))
        {
            Toast.makeText(this, "Not Valid password", Toast.LENGTH_SHORT).show()
            return
        }

        */
        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener()
            {
                if(it.isSuccessful)
                {
                    Log.d("Register","CreateUserwithEmail: Sucess")
                    saveUsertoDatabase()
                }
                else
                {
                    Log.w("Register", "createUserWithEmail:failure")
                    Toast.makeText(this,"Registration Failed", Toast.LENGTH_SHORT).show()
                }
            }

    }
    private fun isValidPassowrd(password: String): Boolean {
        //ref https://stackoverflow.com/questions/36574183/how-to-validate-password-field-in-android
        val pattern: Pattern
        val matcher: Matcher
        val PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$"
        pattern = Pattern.compile(PASSWORD_PATTERN)
        matcher = pattern.matcher(password)

        return matcher.matches()
    }
    private fun saveUsertoDatabase()
    {
        val uid= auth.uid ?: ""
        val ref= FirebaseDatabase.getInstance().getReference("/users/$uid")
        val user= User(uid, Fname_register.text.toString(),LName_register.text.toString(),email_edittext_register.text.toString(),Aptno_register.text.toString(),0)
        //ref.child("resident").child(uid).setValue(user)
        ref.setValue(user)
            .addOnSuccessListener {
                Log.d("Register","Saved user to data base with uid of $uid")
                val intent= Intent(this,MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.putExtra("isadmin",0)
                //finish()
                startActivity(intent)
            }
            .addOnFailureListener()
            {
                Log.d("Register", "Failed to set value to database: ${it.message}")
            }

    }


}

