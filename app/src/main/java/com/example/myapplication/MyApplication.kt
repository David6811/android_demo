package com.example.myapplication

import android.app.Application

class MyApplication : Application() {
    val appComponent = DaggerApplicationComponent.builder()
        .appModule(AppModule(this))  // ✅ Pass application context
        .build()
}

