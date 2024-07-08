package com.example.notesappwithkotlin.data.repository

import android.net.Uri
import com.example.notesappwithkotlin.data.model.Note
import com.example.notesappwithkotlin.data.model.User
import com.example.notesappwithkotlin.util.UiState

interface NoteRepository {
    fun getNotes(user: User?, result: (UiState<List<Note>>) -> Unit)
    fun addNote(note: Note, result: (UiState<Pair<Note,String>>) -> Unit)
    fun updateNote(note: Note, result: (UiState<String>) -> Unit)
    fun deleteNote(note: Note, result: (UiState<String>) -> Unit)
    suspend fun uploadSingleFile(fileUri: Uri, onResult: (UiState<Uri>) -> Unit)
    suspend fun uploadMultipleFile(fileUri: List<Uri>, onResult: (UiState<List<Uri>>) -> Unit)
}