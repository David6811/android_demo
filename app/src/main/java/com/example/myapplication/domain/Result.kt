package com.example.myapplication.domain

sealed interface DeleteNoteResult

data object DeleteNoteSuccess : DeleteNoteResult

data class DeleteNoteError(val e: Throwable) : DeleteNoteResult