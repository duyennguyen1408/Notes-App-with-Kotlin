package com.example.notesappwithkotlin.data.repository

import com.example.notesappwithkotlin.data.model.Task
import com.example.notesappwithkotlin.util.UiState

interface TaskRepository {
    fun addTask(task: Task, result: (UiState<Pair<Task,String>>) -> Unit)
}