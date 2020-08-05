package com.junka.jnknotes.domain

import androidx.lifecycle.LiveData
import com.junka.jnknotes.data.NotesDataBase
import com.junka.jnknotes.data.entities.Note

class RepositoryImpl(private val database: NotesDataBase) : Repository {

    override fun getNotes(): LiveData<List<Note>> {
        return database.noteDao.getAllNotes()
    }

    override suspend fun insertNote(note: Note): Long {
        return database.noteDao.insert(note)

    }

    override suspend fun updateNote(note: Note): Int {
        return database.noteDao.update(note)
    }

    override suspend fun getNote(id: Long): Note {
        return database.noteDao.getNote(id)
    }


}

