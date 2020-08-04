package com.junka.jnknotes.domain

import com.junka.jnknotes.data.entities.Note

interface Repository {
    suspend fun getNotes(): List<Note>
    suspend fun insertNote(note: Note): Long
    suspend fun updateNote(note: Note): Int
    suspend fun getNote(id: Long): Note
}