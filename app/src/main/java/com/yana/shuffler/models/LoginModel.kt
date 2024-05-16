package com.yana.shuffler.models

import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.yana.shuffler.models.enumclasses.AuthResult
import com.yana.shuffler.contracts.LoginContract

class LoginModel : LoginContract.Model {
    override fun checkUserAuth(
        email: String,
        password: String,
        loginListener: LoginContract.Model.LoginListener
    ) {
        if (email.isEmpty() || password.isEmpty()) {
            loginListener.onAuthFailure(AuthResult.Required.message)
        } else {
            val mAuth = Firebase.auth
            mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        loginListener.onAuthSuccess()
                    } else {
                        when (task.exception) {
                            is FirebaseNetworkException -> {
                                loginListener.onAuthFailure(AuthResult.NetworkError.message)
                            }
                            is FirebaseAuthInvalidCredentialsException -> {
                                loginListener.onAuthFailure(AuthResult.InvalidCred.message)
                            }
                            else -> {
                                loginListener.onAuthFailure(AuthResult.Others.message)
                            }
                        }
                    }
                }
        }
    }
}