package com.example.notesappwithkotlin.util

object FireStoreCollection{
    val NOTE = "note"
    val USER = "user"
}//Specifies the names of Firestore collections.
object FireDatabase{
    val TASK = "task"
}//Specifies references for the Firebase Realtime Database.
object FireStoreDocumentField {
    val DATE = "date"
    val USER_ID = "user_id"
}//Defines field names for documents in Firestore.
object SharedPrefConstants {
    val LOCAL_SHARED_PREF = "local_shared_pref"
    val USER_SESSION = "user_session"
}//Provides keys for accessing shared preferences data.
object FirebaseStorageConstants {
    val ROOT_DIRECTORY = "app"
    val NOTE_IMAGES = "note"
}//Defines paths for storing files in Firebase Storage.
enum class HomeTabs(val index: Int, val key: String) {
    NOTES(0, "notes"),
    TASKS(1, "tasks"),
}//Enumerates the different tabs in the home screen.


