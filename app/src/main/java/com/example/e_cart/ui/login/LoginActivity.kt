package com.example.e_cart.ui.login

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.e_cart.MainActivity
import com.example.e_cart.R
import com.example.e_cart.data.model.loginModel
import com.example.e_cart.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import java.util.*

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
    }

    override fun onStart() {
        super.onStart()
        = FirebaseAuth.getInstance()
        val currentUser = firebaseAuth.currentUser
        if (currentUser != null) {
            openMainActivity()
        }
        val button: Button = findViewById(R.id.loginButton)
        button.setOnClickListener {
            getUserLoginInfo()
        }


    }

    fun signInGoogle(profile_id: String) {
        val sharedPreferences = getSharedPreferences("LoginId", Context.MODE_PRIVATE)
        sharedPreferences.edit().putString("profile_id", profile_id).apply()
    }

    fun openMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()//?
    }

    fun getUserLoginInfo() {
        val username: String? = binding.username.text.toString()
        val password: String? = binding.password.text.toString()
        if (username == null || password == null)
            Toast.makeText(getApplicationContext(), "Lütfen gerekli alanları doldurunuz!", Toast.LENGTH_SHORT).show();
        else
            loginFunc()
    }

    fun loginFunc() {
     //   firebaseAuth.signInWithEmailAndPassword()
//email and password login
        //google icin yeniden bak
    }


}