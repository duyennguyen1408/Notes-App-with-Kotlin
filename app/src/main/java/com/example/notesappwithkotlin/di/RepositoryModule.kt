package com.example.notesappwithkotlin.di

import android.content.SharedPreferences
import com.example.notesappwithkotlin.data.repository.AuthRepositoryImp
import com.example.notesappwithkotlin.data.repository.AuthRepository
import com.example.notesappwithkotlin.data.repository.NoteRepository
import com.example.notesappwithkotlin.data.repository.NoteRepositoryImp
import com.example.notesappwithkotlin.data.repository.TaskRepository
import com.example.notesappwithkotlin.data.repository.TaskRepositoryImp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Provides
    @Singleton
    fun provideNoteRepository(
        database: FirebaseFirestore,
        storageReference: StorageReference
    ): NoteRepository{
        return NoteRepositoryImp(database,storageReference)
    }

    @Provides
    @Singleton
    fun provideTaskRepository(
        database: FirebaseDatabase
    ): TaskRepository{
        return TaskRepositoryImp(database)
    }

    @Provides
    @Singleton
    fun provideAutghRepository(
        database: FirebaseFirestore,
        auth: FirebaseAuth,
        appPreferences: SharedPreferences,
        gson: Gson
    ): AuthRepository {
        return AuthRepositoryImp(auth,database,appPreferences,gson)
    }
}