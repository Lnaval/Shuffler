package com.yana.shuffler.contracts

import android.app.Activity

interface RegisterContract {
    interface Model{
        interface ModelListener{
            fun registerFail(result: String)
            fun registerSuccess()
        }
        fun registerUser(email: String, password: String, activity: Activity, modelListener: ModelListener)
    }

    interface Presenter {
        fun verifyUserInfoInput(email: String, password: String, activity: Activity)
    }

    interface View {
        fun displayRegisterResult(result: String)
        fun displayRegisterSuccess()
    }
}