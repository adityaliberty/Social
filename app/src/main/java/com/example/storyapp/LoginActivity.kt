package com.example.storyapp

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.storyapp.daos.UserDao
import com.example.storyapp.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*


class LoginActivity : AppCompatActivity() {


    private lateinit var Auth: FirebaseAuth



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        Auth = Firebase.auth


        Auth = FirebaseAuth.getInstance()
        val currentUser = Auth.currentUser
        if(currentUser != null){

            startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
            finish()
        }
        login()


    }
    override fun onStart() {
        super.onStart()
        val currentUser = Auth.currentUser
        updateUI(currentUser)
    }

    private fun login() {

        loginButton.setOnClickListener {
            if (TextUtils.isEmpty(inputEmail.text.toString())) {
                inputEmail.error = "Enter Your Email"
                return@setOnClickListener
            } else if (TextUtils.isEmpty(inputPassword.text.toString())) {
                inputPassword.error = "Enter Your Email"
                return@setOnClickListener

            }
            Auth.signInWithEmailAndPassword(
                inputEmail.text.toString(),
                inputPassword.text.toString()
            )
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(
                            this@LoginActivity,
                            "Login     failed, Please try again",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
        }
        RegisterText.setOnClickListener {
            startActivity(Intent(this@LoginActivity, Register::class.java))
        }

    }

    private fun updateUI(firebaseUser: FirebaseUser?) {
        if(firebaseUser != null) {

            val user = User(firebaseUser.uid, firebaseUser.displayName, firebaseUser.photoUrl.toString())
            val usersDao = UserDao()
            usersDao.addUser(user)

            val mainActivityIntent = Intent(this, MainActivity::class.java)
            startActivity(mainActivityIntent)
            finish()
        }
    }
}

}