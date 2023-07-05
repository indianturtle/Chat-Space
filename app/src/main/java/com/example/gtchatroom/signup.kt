package com.example.gtchatroom

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class signup : AppCompatActivity() {
    private lateinit var edtName : EditText
    private lateinit var edtEmail : EditText
    private lateinit var edtPass : EditText
    private lateinit var btnSignup : Button
    private lateinit var mAuth : FirebaseAuth
    private lateinit var mDbref: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        mAuth = FirebaseAuth.getInstance()
        edtPass = findViewById(R.id.edt_password)
        edtName = findViewById(R.id.signupname)
        edtEmail = findViewById(R.id.signupemail)
        btnSignup = findViewById(R.id.SignInBtn)
        btnSignup.setOnClickListener{
            val name = edtName.text.toString()
            val email = edtEmail.text.toString()
            val password = edtPass.text.toString()

            signUp(name,email,password)
        }
    }
    private fun signUp(name:String,email:String,password:String){
        //logic of creating user
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    // Sign in success
                    addUserToDatabase(name,email,mAuth.currentUser?.uid!!)      //func call to add user
                    val intent=Intent(this,MainActivity::class.java)
                    finish()
                    startActivity(intent)
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this, "some error occured", Toast.LENGTH_SHORT).show()
                }
            }
    }
    private fun addUserToDatabase(name: String,email: String,uid:String){
        mDbref = FirebaseDatabase.getInstance().reference
        mDbref.child("user").child(uid).setValue(user(name,email,uid))
    }
}