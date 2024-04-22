package com.yana.shuffler.presenters

import com.yana.shuffler.contracts.RegisterContract

class RegisterPresenter(
    private var mainView: RegisterContract.View?,
    private val model: RegisterContract.Model
) : RegisterContract.Presenter, RegisterContract.Model.Listener{
    override fun onClickRegister(email: String, password: String) {
        model.registerUser(email, password, this)
    }

    override fun onSuccess() {
        mainView!!.displayRegisterSuccess()
    }

    override fun onFailure(result: String) {
        mainView!!.displayRegisterResult(result)
    }
}