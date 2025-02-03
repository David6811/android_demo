package com.example.myapplication.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myapplication.entities.Note

@Database(entities = [Note::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao  // Provides access to the NoteDao
}
