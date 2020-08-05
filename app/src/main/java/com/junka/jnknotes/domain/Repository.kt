package com.junka.jnknotes.domain

import androidx.lifecycle.LiveData
import com.junka.jnknotes.data.entities.Note

interface Repository {
    fun getNotes(): LiveData<List<Note>>
    suspend fun insertNote(note: Note): Long
    suspend fun updateNote(note: Note): Int
    suspend fun getNote(id: Long): Note
    suspend fun deleteNote(note: Note)
}