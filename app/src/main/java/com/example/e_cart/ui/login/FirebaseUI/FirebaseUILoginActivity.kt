package com.example.e_cart.ui.login.FirebaseUI

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.e_cart.ui.main.MainActivity
import com.example.e_cart.R
import com.example.e_cart.databinding.ActivityFirebaseuiLoginBinding
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.ErrorCodes
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.firebase.ui.auth.util.ExtraConstants
import com.google.firebase.auth.ActionCodeSettings
import com.google.firebase.auth.FirebaseAuth

class FirebaseUILoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFirebaseuiLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var authUI: AuthUI

    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { res ->
        this.onSignInResult(res)
    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_firebaseui_login)
        setContentView(R.layout.activity_firebaseui_login)
    }

    override fun onStart() {
        super.onStart()
        firebaseAuth = FirebaseAuth.getInstance()
        val button: Button = findViewById(R.id.loginButton)
        button.setOnClickListener {
            getUserLoginInfo()
        }
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
            Toast.makeText(applicationContext, "Please fill the empty spaces!", Toast.LENGTH_SHORT).show();
        else
            loginFunc()
    }

    fun loginFunc() {

        authUI = AuthUI.getInstance()
        val intentGoogle = authUI.createSignInIntentBuilder().setAvailableProviders(
            listOf(AuthUI.IdpConfig.GoogleBuilder().build(), AuthUI.IdpConfig.EmailBuilder().build())
        ).build()
        signInLauncher.launch(intentGoogle)

    }

    fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        val response = result.idpResponse
        if (result.resultCode == RESULT_OK) {
            val currentUser = FirebaseAuth.getInstance().currentUser
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            if (response == null) {
                //sign in canceled
                return
            }
            if (response.error?.errorCode == ErrorCodes.NO_NETWORK) {
                //no internet connection
                return
            }
            /// or default error
            //sign in errror
            Log.e("ErrorTAG", "Sign-in error: ", response.getError());
        }
    }

    private fun delete() {
        // [START auth_fui_delete]
        AuthUI.getInstance()
            .delete(this)
            .addOnCompleteListener {
                // ...
            }

    }

    private fun privacyAndTerms() {
        val providers = emptyList<AuthUI.IdpConfig>()
        // [START auth_fui_pp_tos]
        val signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .setTosAndPrivacyPolicyUrls(
                "https://example.com/terms.html",
                "https://example.com/privacy.html"
            )
            .build()
        signInLauncher.launch(signInIntent)

    }

    open fun emailLink() {
        val actionCodeSettings = ActionCodeSettings.newBuilder()
            .setAndroidPackageName("com.example.e_cart", true, null)
            .setHandleCodeInApp(true).setUrl("https://google.com").build()

        val providers = listOf(
            AuthUI.IdpConfig.EmailBuilder()
                .enableEmailLinkSignIn()
                .setActionCodeSettings(actionCodeSettings)
                .build()
        )

        val signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .build()

        signInLauncher.launch(signInIntent)
    }

    open fun catchEmailLink() {
        val providers: List<AuthUI.IdpConfig> = emptyList()
        if (AuthUI.canHandleIntent(intent)) {
            val extras = intent.extras ?: return
            val link = extras.getString(ExtraConstants.EMAIL_LINK_SIGN_IN)
            if (link != null) {
                val signInIntent = AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setEmailLink(link)
                    .setAvailableProviders(providers)
                    .build()
                signInLauncher.launch(signInIntent)
            }
        }

    }

}