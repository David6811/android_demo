package com.example.myapplication

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.myapplication.dao.AppDatabase
import com.example.myapplication.dao.NoteDao
import com.example.myapplication.entities.Note
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class NoteDaoTest {

    private lateinit var noteDao: NoteDao
    private lateinit var db: AppDatabase

    @Before
    fun setUp() {
        // Create an in-memory Room database for testing
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries() // You can allow queries on the main thread for testing
            .build()
        noteDao = db.noteDao() // Access the NoteDao instead of IconDao
    }

    @After
    fun tearDown() {
        // Close the database after the test
        db.close()
    }

    @Test
    fun insertAndGetAllNotes() {
        // Create a list of Notes to insert
        val note1 = Note(
            objectId = "1",
            title = "Title of Note 1",
            content = "Content of Note 1",
            parentObjectId = "0",
            status = 1,
            tags = "tag1",
            createdAt = "2025-02-03T10:00:00Z",
            updatedAt = "2025-02-03T10:00:00Z"
        )
        val note2 = Note(
            objectId = "2",
            title = "Title of Note 2",
            content = "Content of Note 2",
            parentObjectId = "0",
            status = 1,
            tags = "tag2",
            createdAt = "2025-02-03T10:00:00Z",
            updatedAt = "2025-02-03T10:00:00Z"
        )

        // Insert notes
        noteDao.insertAll(note1, note2)

        // Get all notes from the database
        val notes = noteDao.getAll()

        // Assert that the notes were correctly inserted
        assertNotNull(notes)
        assertEquals(2, notes.size)
        assertTrue(notes.contains(note1))
        assertTrue(notes.contains(note2))
    }

    @Test
    fun deleteNote() {
        // Create a note to insert and delete
        val note = Note(
            objectId = "1",
            title = "Title of Note 1",
            content = "Content of Note 1",
            parentObjectId = "0",
            status = 1,
            tags = "tag1",
            createdAt = "2025-02-03T10:00:00Z",
            updatedAt = "2025-02-03T10:00:00Z"
        )

        // Insert the note
        noteDao.insertAll(note)

        // Delete the note
        noteDao.delete(note)

        // Get all notes and ensure the note is deleted
        val notes = noteDao.getAll()
        assertFalse(notes.contains(note))
    }

    @Test
    fun deleteAllNotes() {
        // Create a list of notes to insert
        val note1 = Note(
            objectId = "1",
            title = "Title of Note 1",
            content = "Content of Note 1",
            parentObjectId = "0",
            status = 1,
            tags = "tag1",
            createdAt = "2025-02-03T10:00:00Z",
            updatedAt = "2025-02-03T10:00:00Z"
        )
        val note2 = Note(
            objectId = "2",
            title = "Title of Note 2",
            content = "Content of Note 2",
            parentObjectId = "0",
            status = 1,
            tags = "tag2",
            createdAt = "2025-02-03T10:00:00Z",
            updatedAt = "2025-02-03T10:00:00Z"
        )

        // Insert notes
        noteDao.insertAll(note1, note2)

        // Delete all notes
        noteDao.deleteAll()

        // Get all notes and ensure the list is empty
        val notes = noteDao.getAll()
        assertTrue(notes.isEmpty())
    }
}
