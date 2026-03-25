package com.example.sqlpractice

import android.app.Application
import com.example.sqlpractice.Data.AppContainer
import com.example.sqlpractice.Data.AppDataContainer

class PostsApplication: Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}