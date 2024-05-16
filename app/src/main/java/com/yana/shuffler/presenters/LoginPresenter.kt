package com.yana.shuffler.presenters

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

    override fun onClickLogin(email: String, password: String) {
        model.checkUserAuth(email, password, this)
    }
}