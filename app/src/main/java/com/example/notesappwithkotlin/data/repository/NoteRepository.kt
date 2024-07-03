package com.example.notesappwithkotlin.data.repository

import com.example.notesappwithkotlin.data.model.Note

interface NoteRepository {
    fun getNotes(): List<Note>
}