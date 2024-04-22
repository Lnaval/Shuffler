package com.yana.shuffler.contracts

import com.yana.shuffler.AuthResult

interface LoginContract {
    interface Model {
        fun checkUserAuth(email: String, password: String) : AuthResult
    }

    interface Presenter{
        fun onClickLogin(email: String, password: String)
    }

    interface View{
       fun displayOnError(result: String)
       fun displayOnSuccess()
    }
}