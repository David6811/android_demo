package com.example.myapplication

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class]) // ✅ Ensure AppModule is included
interface ApplicationComponent {
    fun inject(activity: MainActivity)  // ✅ Allow injection in MainActivity
}
