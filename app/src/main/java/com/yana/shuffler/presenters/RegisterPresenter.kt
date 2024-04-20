package com.yana.shuffler.presenters

import android.app.Activity
import com.yana.shuffler.contracts.RegisterContract

class RegisterPresenter(
    private var mainView: RegisterContract.View?,
    private val model: RegisterContract.Model
) : RegisterContract.Presenter, RegisterContract.Model.ModelListener {
    override fun registerFail(result: String) {
        mainView!!.displayRegisterResult(result)
    }

    override fun registerSuccess() {
        mainView!!.displayRegisterSuccess()
    }

    override fun verifyUserInfoInput(email: String, password: String, activity: Activity) {
        model.registerUser(email, password, activity, this)
    }
}