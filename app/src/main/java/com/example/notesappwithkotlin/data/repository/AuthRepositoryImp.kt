package com.example.notesappwithkotlin.data.repository

import android.content.SharedPreferences
import com.example.notesappwithkotlin.data.model.User
import com.example.notesappwithkotlin.util.FireStoreCollection
import com.example.notesappwithkotlin.util.UiState
import com.example.notesappwithkotlin.data.repository.AuthRepository
import com.example.notesappwithkotlin.util.SharedPrefConstants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
class AuthRepositoryImp(    //Initializes Firebase Auth, Firestore, SharedPreferences, and Gson.
    val auth: FirebaseAuth,
    val database: FirebaseFirestore,
    val appPreferences: SharedPreferences,
    val gson: Gson
) : AuthRepository {
    override fun registerUser(
        email: String,
        password: String,
        user: User, result: (UiState<String>) -> Unit
    )//Registers a user and handles success or failure.
    {
        auth.createUserWithEmailAndPassword(email,password) //// Create a new user with the email, password with Firebase Auth.
            .addOnCompleteListener {
                if (it.isSuccessful){//Handle Success: Sets user ID, updates user info, and stores session.
                    user.id = it.result.user?.uid ?: ""
                    updateUserInfo(user) { state ->
                        when(state){
                            is UiState.Success -> {
                                storeSession(id = it.result.user?.uid ?: "") {// Updating user info successfully, store the session
                                    if (it == null){
                                        result.invoke(UiState.Failure("User register successfully but session failed to store"))
                                    }else{
                                        result.invoke(
                                            UiState.Success("User register successfully!")
                                        )
                                    }
                                }
                            }
                            is UiState.Failure -> {
                                result.invoke(UiState.Failure(state.error))
                            }
                            is UiState.Loading -> {

                            }
                        }
                    }
                }else{
                    try {
                        throw it.exception ?: java.lang.Exception("Invalid authentication")
                    } catch (e: FirebaseAuthWeakPasswordException) {
                        result.invoke(UiState.Failure("Authentication failed, Password should be at least 6 characters"))
                    } catch (e: FirebaseAuthInvalidCredentialsException) {
                        result.invoke(UiState.Failure("Authentication failed, Invalid email entered"))
                    } catch (e: FirebaseAuthUserCollisionException) {
                        result.invoke(UiState.Failure("Authentication failed, Email already registered."))
                    } catch (e: Exception) {
                        result.invoke(UiState.Failure(e.message))
                    }
                }
            }
            .addOnFailureListener {//Calls failure callback with an error message.
                result.invoke(
                    UiState.Failure(
                        it.localizedMessage
                    )
                )
            }
    }
    override fun updateUserInfo(user: User, result: (UiState<String>) -> Unit) {
        // Get the document reference for the user in the Firestore database
        val document = database.collection(FireStoreCollection.USER).document(user.id)
        // Set the user's information in the document
        document
            .set(user)
            .addOnSuccessListener {
                result.invoke(
                    UiState.Success("User has been update successfully")
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
    override fun loginUser(
        email: String,
        password: String,
        result: (UiState<String>) -> Unit) {
        // Sign in the user with the provided email and password using Firebase Auth
        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {// Check if the sign-in was successful
                    // Store the user session locally
                    storeSession(id = task.result.user?.uid ?: ""){
                        // Check if storing the session failed or succeeded
                        if (it == null){
                            result.invoke(UiState.Failure("Failed to store local session"))
                        }else{
                            result.invoke(UiState.Success("Login successfully!"))
                        }
                    }
                }
            }.addOnFailureListener {
                result.invoke(UiState.Failure("Authentication failed, Check email and password"))
            }
    }
    override fun forgotPassword(email: String, result: (UiState<String>) -> Unit) {
        // Send a password reset email to the email using Firebase Auth
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                // Check if sending the email was successful
                if (task.isSuccessful) {
                    result.invoke(UiState.Success("Email has been sent"))

                } else {
                    result.invoke(UiState.Failure(task.exception?.message))
                }
            }.addOnFailureListener {
                result.invoke(UiState.Failure("Authentication failed, Check email"))
            }
    }
    override fun logout(result: () -> Unit) {
        // Sign out the user from Firebase Auth
        auth.signOut()
        // Clear the user session from shared preferences
        appPreferences.edit().putString(SharedPrefConstants.USER_SESSION,null).apply()
        result.invoke()
    }
    override fun storeSession(id: String, result: (User?) -> Unit) {
        // Get the document reference for the user in the Firestore
        database.collection(FireStoreCollection.USER).document(id)
            // Retrieve the document from Firestore
            .get()
            .addOnCompleteListener {
                // Check if the retrieval was successful
                if (it.isSuccessful){
                    val user = it.result.toObject(User::class.java)// Convert the document snapshot to a User object
                    // Store the user object as a JSON string in shared preferences
                    appPreferences.edit().putString(SharedPrefConstants.USER_SESSION,gson.toJson(user)).apply()
                    result.invoke(user)
                }else{
                    result.invoke(null)
                }
            }
            .addOnFailureListener {
                result.invoke(null)
            }
    }
    override fun getSession(result: (User?) -> Unit) {
        // Retrieve the user session string from shared preferences
        val user_str = appPreferences.getString(SharedPrefConstants.USER_SESSION,null)
        if (user_str == null){// Check if the user session string is null
            result.invoke(null)
        }else{
            //convert the stored session data back into User object.
            val user = gson.fromJson(user_str,User::class.java)
            result.invoke(user)
        }
    }
}

