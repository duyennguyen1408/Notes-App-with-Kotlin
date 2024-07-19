package com.example.notesappwithkotlin.di

import android.content.Context
import android.content.SharedPreferences
import com.example.notesappwithkotlin.util.SharedPrefConstants
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {
    @Provides
    @Singleton
//    Supplies a singleton instance of SharedPreferences for local data storage.
    fun provideSharedPref(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(SharedPrefConstants.LOCAL_SHARED_PREF,Context.MODE_PRIVATE)
    }
    @Provides
    @Singleton
//    Creates and provides a singleton instance of Gson, a library used to convert Java/Kotlin objects to JSON and vice versa.
    fun provideGson(): Gson {
        return Gson()
    }
}
