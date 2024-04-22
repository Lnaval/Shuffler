package com.yana.shuffler.models

import android.app.Activity
import android.util.Log
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.yana.shuffler.AuthResult
import com.yana.shuffler.contracts.LoginContract

class LoginModel(private val activity: Activity) : LoginContract.Model {
    override fun checkUserAuth(email: String, password: String): AuthResult {
        if (email.isEmpty() || password.isEmpty()) {
            return AuthResult.Required
        } else {
            val mAuth = Firebase.auth
            var status = AuthResult.Others
            mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        status = AuthResult.Success
                    } else {
                        when (task.exception) {
                            is FirebaseNetworkException -> status = AuthResult.NetworkError
                            is FirebaseAuthInvalidCredentialsException -> status = AuthResult.InvalidCred
                            else -> status = AuthResult.Others
                        }
                    }
                }
            return status
        }
    }
}