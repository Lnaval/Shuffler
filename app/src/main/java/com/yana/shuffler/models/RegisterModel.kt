package com.yana.shuffler.models

import android.app.Activity
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.yana.shuffler.contracts.RegisterContract

class RegisterModel : RegisterContract.Model{
    override fun registerUser(
        email: String,
        password: String,
        activity: Activity,
        modelListener: RegisterContract.Model.ModelListener
    ) {
        if (email.isEmpty() || password.isEmpty()) {
            modelListener.registerFail("Both fields are required")
        } else {
            val mAuth = Firebase.auth

            mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity) { task ->
                    if (task.isSuccessful) {
                        modelListener.registerSuccess()
                    } else {
                        when(task.exception){
                            is FirebaseAuthUserCollisionException ->
                                modelListener.registerFail("The email address is already in use by another account")
                            is FirebaseAuthInvalidCredentialsException ->
                                modelListener.registerFail("The email address is badly formatted.")
                            else ->
                                modelListener.registerFail("Authentication failed")
                        }
                    }
                }
        }
    }
}