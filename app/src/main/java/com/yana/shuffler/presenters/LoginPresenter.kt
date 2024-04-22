package com.yana.shuffler.presenters

import android.app.Activity
import com.yana.shuffler.AuthResult
import com.yana.shuffler.contracts.LoginContract

class LoginPresenter(
    private var mainView: LoginContract.View?,
    private val model: LoginContract.Model
) : LoginContract.Presenter {

    override fun onClickLogin(email: String, password: String) {
        val result = model.checkUserAuth(email, password)
        if(result==AuthResult.Success){
            mainView!!.displayOnSuccess()
        } else {
            mainView!!.displayOnError(result.message)
        }
    }
}