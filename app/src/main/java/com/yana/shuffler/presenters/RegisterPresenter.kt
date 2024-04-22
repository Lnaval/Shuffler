package com.yana.shuffler.presenters

import android.app.Activity
import com.yana.shuffler.AuthResult
import com.yana.shuffler.contracts.RegisterContract

class RegisterPresenter(
    private var mainView: RegisterContract.View?,
    private val model: RegisterContract.Model
) : RegisterContract.Presenter{
    override fun onClickRegister(email: String, password: String) {
        val result = model.registerUser(email, password)
        if(result == AuthResult.Success){
            mainView!!.displayRegisterSuccess()
        } else {
            mainView!!.displayRegisterResult(result.message)
        }
    }
}