package com.yana.shuffler.contracts
interface LoginContract {
    interface Model {
        interface LoginListener {
            fun onAuthSuccess()
            fun onAuthFailure(result: String)
        }
        fun checkUserAuth(email: String, password: String, loginListener: LoginListener)
    }

    interface Presenter{
        fun onClickLogin(email: String, password: String)
    }

    interface View{
        fun displayOnError(result: String)
        fun displayOnSuccess()
    }
}