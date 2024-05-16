package com.yana.shuffler

import android.app.Application
import com.yana.shuffler.container.AppContainer

class ShufflerApp : Application() {
    companion object{
        lateinit var appContainer: AppContainer
    }

    override fun onCreate() {
        super.onCreate()
        appContainer = AppContainer(this)
    }
}