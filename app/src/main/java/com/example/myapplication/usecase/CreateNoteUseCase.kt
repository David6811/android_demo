package com.example.myapplication.usecase

import com.example.myapplication.dao.NoteDao
import com.example.myapplication.entities.Note
import io.reactivex.rxjava3.core.Completable
import javax.inject.Inject

class CreateNoteUseCase @Inject constructor(private val noteDao: NoteDao) {

    fun insertNotes(note: Note): Completable {
        return Completable
            .fromCallable { noteDao.insertAll(note) }
    }
}
