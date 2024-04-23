package com.yana.shuffler.models

import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.yana.shuffler.models.enumclasses.AuthResult
import com.yana.shuffler.contracts.RegisterContract

class RegisterModel : RegisterContract.Model{
    override fun registerUser(
        email: String,
        password: String,
        listener: RegisterContract.Model.Listener
    ) {
        if (email.isEmpty() || password.isEmpty()) {
            listener.onFailure(AuthResult.Required.message)
        } else {
            val mAuth = Firebase.auth
            mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener{ task ->
                    if (task.isSuccessful) {
                        listener.onSuccess()
                    } else {
                        when(task.exception){
                            is FirebaseAuthUserCollisionException -> {
                                listener.onFailure(AuthResult.InUse.message)
                            }
                            is FirebaseAuthInvalidCredentialsException -> {
                                listener.onFailure(AuthResult.BadFormat.message)
                            }
                            else -> {
                                listener.onFailure(AuthResult.Others.message)
                            }
                        }
                    }
                }
        }
    }
}