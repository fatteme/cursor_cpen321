package com.biteswipe

import android.app.Application
import com.biteswipe.data.auth.AuthManager

class BiteSwipeApplication : Application() {
    lateinit var authManager: AuthManager
        private set

    override fun onCreate() {
        super.onCreate()
        authManager = AuthManager(this)
    }
} 