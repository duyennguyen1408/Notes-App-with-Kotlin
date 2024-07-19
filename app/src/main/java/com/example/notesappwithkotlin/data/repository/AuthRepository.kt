package com.example.notesappwithkotlin.data.repository

import com.example.notesappwithkotlin.data.model.Note
import com.example.notesappwithkotlin.data.model.User
import com.example.notesappwithkotlin.util.UiState

interface AuthRepository {
    fun registerUser(email: String, password: String, user: User, result: (UiState<String>) -> Unit)
//    Registers a new user and returns result (UiState<String>).
    fun updateUserInfo(user: User, result: (UiState<String>) -> Unit)
//    Updates user info and returns result (UiState<String>).
    fun loginUser(email: String, password: String, result: (UiState<String>) -> Unit)
//    Logs in a user and returns result (UiState<String>).
    fun forgotPassword(email: String, result: (UiState<String>) -> Unit)
//    Sends password recovery email and returns result (UiState<String>)
    fun logout(result: () -> Unit)
//    Logs out user and optionally executes a callback.
    fun storeSession(id: String, result: (User?) -> Unit)
//    Stores user session and returns user object.
    fun getSession(result: (User?) -> Unit)
//    Retrieves current user's session and returns user object.
}

