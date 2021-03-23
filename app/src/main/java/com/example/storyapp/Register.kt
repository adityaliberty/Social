package com.example.storyapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_register.*

class Register : AppCompatActivity() {

    lateinit var  Auth: FirebaseAuth
    var databaseReference : DatabaseReference ? = null
    var database : FirebaseDatabase ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        Auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("profile")
        register()
    }

    private fun register(){
        signupButton.setOnClickListener{

            if(TextUtils.isEmpty(inputName.text.toString())){
                inputName.error = "Enter Your Name"
                return@setOnClickListener
            }
            else if(TextUtils.isEmpty(SignupEmail.text.toString())){
                inputName.error = "Enter Your Email"
                return@setOnClickListener
            }
            else if(TextUtils.isEmpty(SignupPassword.text.toString())){
                inputName.error = "Enter Your Password"
                return@setOnClickListener
            }

            Auth.createUserWithEmailAndPassword(SignupEmail.text.toString(), SignupPassword.text.toString())
                .addOnCompleteListener{
                    if(it.isSuccessful){
                        val currentUser =  Auth.currentUser

                        var currentUserDb = databaseReference?.child(currentUser?.uid!!)
                        currentUserDb?.child("name")?.setValue(inputName.text.toString())

                        Toast.makeText(this@Register,"SignUp Success", Toast.LENGTH_LONG).show()
                        finish()

                    }else{
                        Toast.makeText(this@Register,"Check Email or Password", Toast.LENGTH_LONG).show()
                    }
                }

        }
    }
}