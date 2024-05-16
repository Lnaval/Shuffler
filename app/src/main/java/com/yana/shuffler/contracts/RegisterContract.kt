package com.yana.shuffler.contracts
interface RegisterContract {
    interface Model{
        interface Listener{
            fun onSuccess()
            fun onFailure(result: String)
        }
        fun registerUser(email: String, password: String, listener: Listener)
    }

    interface Presenter {
        fun onClickRegister(email: String, password: String)
    }

    interface View {
        fun displayRegisterResult(result: String)
        fun displayRegisterSuccess()
    }
}