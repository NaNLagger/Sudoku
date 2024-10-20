package com.nanlagger.sudoku.di

import android.content.Context

object ComponentManager {

    private var appComponent: AppComponent? = null

    fun initAppComponent(context: Context): AppComponent {
        return AppComponent::class.create(context).also { appComponent = it }
    }

    fun getAppComponent(): AppComponent {
        return appComponent ?: throw RuntimeException("AppComponent not found")
    }
}