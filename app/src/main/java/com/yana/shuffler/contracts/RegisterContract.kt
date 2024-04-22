package com.yana.shuffler.contracts

import com.yana.shuffler.AuthResult

interface RegisterContract {
    interface Model{
        fun registerUser(email: String, password: String) : AuthResult
    }

    interface Presenter {
        fun onClickRegister(email: String, password: String)
    }

    interface View {
        fun displayRegisterResult(result: String)
        fun displayRegisterSuccess()
    }
}