package com.yana.shuffler.models

import android.app.Activity
import android.util.Log
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.yana.shuffler.contracts.LoginContract

class LoginModel : LoginContract.Model {
    override fun checkUserAuth(
        email: String,
        password: String,
        activity: Activity,
        loginListener: LoginContract.Model.LoginListener
    ) {
        if (email.isEmpty() || password.isEmpty()) {
            loginListener.onAuthFailure("Both fields are required")
        } else {
            val mAuth = Firebase.auth
            mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity) { task ->
                    if (task.isSuccessful) {
                        loginListener.onAuthSuccess()
                    } else {
                        Log.e("TAG", "checkUserAuth: ${task.exception}", )
                        when (task.exception) {
                            is FirebaseNetworkException -> {
                                loginListener.onAuthFailure("Network Error")
                            }
                            is FirebaseAuthInvalidCredentialsException -> {
                                loginListener.onAuthFailure("Invalid Credentials")
                            }
                            else -> {
                                loginListener.onAuthFailure("Something went wrong")
                            }
                        }
                    }
                }
        }
    }
}