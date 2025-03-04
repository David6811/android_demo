package com.example.myapplication

import android.content.Context
import androidx.room.Room
import com.example.myapplication.dao.AppDatabase
import com.example.myapplication.dao.NoteDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val context: Context) {

    @Singleton
    @Provides
    fun provideDatabase(): AppDatabase {
        println("Dagger: Providing AppDatabase...")  // Debugging line
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "notes_database"
        ).build()
    }

    @Singleton
    @Provides
    fun provideNoteDao(database: AppDatabase): NoteDao {
        println("Dagger: Providing NoteDao...")  // Debugging line
        return database.noteDao()  // ✅ Provide NoteDao properly
    }
}
