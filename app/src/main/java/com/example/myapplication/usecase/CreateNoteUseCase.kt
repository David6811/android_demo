package com.example.myapplication.usecase

import com.example.myapplication.dao.NoteDao
import com.example.myapplication.entities.Note
import javax.inject.Inject

class CreateNoteUseCase @Inject constructor(private val noteDao: NoteDao) {

    fun insertNotes(vararg notes: Note) {
        noteDao.insertAll(*notes)
    }
}
