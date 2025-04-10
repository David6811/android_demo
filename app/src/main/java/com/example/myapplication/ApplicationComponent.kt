package com.example.myapplication

import com.example.myapplication.ui.home.HomeFragment
import com.example.myapplication.ui.notes.NotesFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class]) // ✅ Ensure AppModule is included
interface ApplicationComponent {
    fun inject(activity: MainActivity)  // ✅ Allow injection in MainActivity
    fun inject(fragment: NotesFragment)
    fun inject(fragment: HomeFragment)
}
