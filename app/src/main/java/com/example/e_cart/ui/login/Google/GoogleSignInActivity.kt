package com.example.e_cart.ui.login.Google

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.e_cart.R
import com.example.e_cart.data.SavedPreferenceUser
import com.example.e_cart.data.model.ShopListModel
import com.example.e_cart.databinding.ActivityGoogleLoginBinding
import com.example.e_cart.ui.Const
import com.example.e_cart.ui.main.MainActivity
import com.example.e_cart.ui.splash.SplashScreenActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import kotlin.math.log

class GoogleSignInActivity : Activity() {
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var binding: ActivityGoogleLoginBinding
    private lateinit var auth: FirebaseAuth
    private val TAG = "GoogleSignInActivity"
    private val RC_SIGN_IN = 9001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_google_login)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_google_login)
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("270548447840-i0ueutmf9d1t3me8in7s9cn6a0mf8apr.apps.googleusercontent.com").requestEmail().build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
        auth = Firebase.auth
    }

    override fun onStart() {
        super.onStart()

//        if (auth.currentUser != null) {
////            val shared=getPreferences(Context.MODE_PRIVATE)
////            val emailUser=shared.getString("EMAIL","email")
////            Log.d("MainActicityOpenedTAG", emailUser!!)
//            openMainActivity()
//
//        }
        binding.signInButton.setOnClickListener {
            signIn()
        }
    }

    private fun signIn() {
        binding.signInButton.visibility = View.GONE
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val accountData = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(accountData)
            } catch (e: ApiException) {
                Log.w(TAG, "signInWithCredential:failure", task.exception)
            }
        }
    }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount?) {
        val idToken = account?.idToken
        val credential = GoogleAuthProvider.getCredential(idToken, null)

        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithCredential:success")
                    val sharedPreferences = getSharedPreferences("e_cart_preferences",Context.MODE_PRIVATE)
                    SavedPreferenceUser.setUser(sharedPreferences, account!!)
                    val databaseReference = FirebaseDatabase.getInstance()
                    databaseReference.reference
                        .child(Const.firstCollectionName)
                        .child(account.id.toString())
                        .child("listBasket").setValue(ShopListModel(title = ""))
                    openMainActivity()
                } else {
                    Log.d("failure:", idToken!!)
                    Log.w(TAG, "signInWithCredential", task.exception)
                }
            }
    }

    private fun openMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }


}

