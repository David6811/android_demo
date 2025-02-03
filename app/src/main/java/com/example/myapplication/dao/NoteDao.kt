package com.example.myapplication.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.myapplication.entities.Note

@Dao
interface NoteDao {
    @Insert
    fun insertAll(vararg notes: Note) // Using vararg for flexibility

    @Delete
    fun delete(note: Note)

    @Query("SELECT * FROM note")
    fun getAll(): List<Note>

    @Query("DELETE FROM note")
    fun deleteAll() // Clear all notes from the database
}
