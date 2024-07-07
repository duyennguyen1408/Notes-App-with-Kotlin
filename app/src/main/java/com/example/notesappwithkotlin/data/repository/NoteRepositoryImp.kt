package com.example.notesappwithkotlin.data.repository

import com.example.notesappwithkotlin.data.model.Note
import com.example.notesappwithkotlin.data.model.User
import com.example.notesappwithkotlin.util.FireStoreCollection
import com.example.notesappwithkotlin.util.FireStoreDocumentField
import com.example.notesappwithkotlin.util.UiState
import com.example.notesappwithkotlin.util.SharedPrefConstants
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import android.content.SharedPreferences

class NoteRepositoryImp(
    val database: FirebaseFirestore
):NoteRepository {
    override fun getNotes(result: (UiState<List<Note>>) -> Unit) {
        database.collection(FireStoreCollection.NOTE)
            .orderBy(FireStoreDocumentField.DATE, Query.Direction.DESCENDING)
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

    override fun addNote(note: Note, result: (UiState<Pair<Note,String>>) -> Unit) {
        val document = database.collection(FireStoreCollection.NOTE).document()
        note.id = document.id
        document
            .set(note)
            .addOnSuccessListener {
                result.invoke(
                    UiState.Success(Pair(note,"Note has been created successfully"))
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
        val document = database.collection(FireStoreCollection.NOTE).document(note.id)
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

    override fun deleteNote(note: Note, result: (UiState<String>) -> Unit) {
        database.collection(FireStoreCollection.NOTE).document(note.id)

            .delete()
            .addOnSuccessListener {
                result.invoke(UiState.Success("Note successfully deleted!"))
            }
            .addOnFailureListener { e ->
                result.invoke(UiState.Failure(e.message))
            }
    }
}