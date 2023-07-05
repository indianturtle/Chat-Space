package com.example.gtchatroom

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {
    private lateinit var edtEmail : EditText
    private lateinit var edtPass : EditText
    private lateinit var btnLogin :Button
    private lateinit var btnSignup : Button

    private lateinit var mAuth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mAuth = FirebaseAuth.getInstance()
        edtEmail = findViewById(R.id.loginemail)
        edtPass = findViewById(R.id.loginpass)
        btnLogin = findViewById(R.id.LogInBtn)
        btnSignup = findViewById(R.id.SignUpBtn)

        btnSignup.setOnClickListener{
            val intent = Intent(this,signup::class.java)

            startActivity(intent)
        }

        btnLogin.setOnClickListener{
            val email = edtEmail.text.toString()
            val password =edtPass.text.toString()

            login(email,password)
        }
    }

    private fun login(email:String,password:String){
        //logic for logging the user
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    //code for logging in user
                    val intent = Intent(this,MainActivity::class.java)
                    finish()
                    startActivity(intent)
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this, "User does not exist", Toast.LENGTH_SHORT).show()
                }
            }
    }
}