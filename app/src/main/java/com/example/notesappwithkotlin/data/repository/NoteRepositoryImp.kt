package com.example.notesappwithkotlin.data.repository

import com.example.notesappwithkotlin.data.model.Note
import com.example.notesappwithkotlin.util.FireStoreTables
import com.example.notesappwithkotlin.util.UiState
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import java.util.*

class NoteRepositoryImp(
    val database: FirebaseFirestore
):NoteRepository {
    override fun getNotes(result: (UiState<List<Note>>) -> Unit) {
    database.collection(FireStoreTables.NOTE)
    .get()
    .addOnSuccessListener {
        val notes = arrayListOf<Note>()
        for (document in it){
            val note = document.toObject(Note::class.java)
            notes.add(note)
        }
        result.invoke(
            UiState.Success(notes)
        )
    }
    .addOnFailureListener {
        result.invoke(
            UiState.Failure(
                it.localizedMessage
            )
        )
    }
    }

    override fun addNote(note: Note, result: (UiState<String>) -> Unit) {
        val document = database.collection(FireStoreTables.NOTE).document()
        note.id = document.id
        document
            .set(note)
            .addOnSuccessListener {
                result.invoke(
                    UiState.Success("Note has been created successfully")
                )
            }
            .addOnFailureListener {
                result.invoke(
                    UiState.Failure(
                        it.localizedMessage
                    )
                )
            }
    }

    override fun updateNote(note: Note, result: (UiState<String>) -> Unit) {
        val document = database.collection(FireStoreTables.NOTE).document(note.id)
        document
            .set(note)
            .addOnSuccessListener {
                result.invoke(
                    UiState.Success("Note has been updated successfully")
                )
            }
            .addOnFailureListener {
                result.invoke(
                    UiState.Failure(
                        it.localizedMessage
                    )
                )
            }
    }
}