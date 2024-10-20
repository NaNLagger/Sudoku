package com.nanlagger.sudoku

import android.app.Application
import com.nanlagger.sudoku.di.ComponentManager

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        ComponentManager.initAppComponent(applicationContext)
    }
}