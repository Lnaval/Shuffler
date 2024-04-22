package com.yana.shuffler.models

import android.app.Activity
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.yana.shuffler.AuthResult
import com.yana.shuffler.contracts.RegisterContract

class RegisterModel(private val  activity: Activity) : RegisterContract.Model{
    override fun registerUser(email: String, password: String): AuthResult {
        if (email.isEmpty() || password.isEmpty()) {
            return AuthResult.Required
        } else {
            val mAuth = Firebase.auth
            var status = AuthResult.Others
            mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        status = AuthResult.Success
                    } else {
                        when(task.exception){
                            is FirebaseAuthUserCollisionException -> status = AuthResult.InUse
                            is FirebaseAuthInvalidCredentialsException -> status = AuthResult.BadFormat
                            else -> status = AuthResult.Others
                        }
                    }
                }
            return status
        }
    }
}