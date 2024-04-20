package com.yana.shuffler.contracts

import android.app.Activity

interface LoginContract {
    interface Model {
        interface LoginListener {
            fun onAuthSuccess()
            fun onAuthFailure(result: String)
        }
        fun checkUserAuth(email: String, password: String, activity: Activity, loginListener: LoginListener)
    }

    interface Presenter{
        fun requestCheckUserInput(email: String, password: String, activity: Activity)
    }

    interface View{
       fun displayOnError(result: String)
       fun displayOnSuccess()
    }
}