package com.yana.shuffler.presenters

import android.app.Activity
import com.yana.shuffler.contracts.LoginContract

class LoginPresenter(
    private var mainView: LoginContract.View?,
    private val model: LoginContract.Model
) : LoginContract.Presenter, LoginContract.Model.LoginListener {
    override fun onAuthSuccess() {
        mainView!!.displayOnSuccess()
    }

    override fun onAuthFailure(result: String) {
        mainView!!.displayOnError(result)
    }

    override fun requestCheckUserInput(email: String, password: String, activity: Activity) {
        model.checkUserAuth(email, password, activity, this)
    }
}