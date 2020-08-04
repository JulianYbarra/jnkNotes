package com.junka.jnknotes.data.dao

import androidx.room.*
import com.junka.jnknotes.data.entities.Note

@Dao
interface NoteDao {

    @Query("SELECT * FROM notes ORDER BY id DESC")
    suspend fun getAllNotes() : List<Note>

    @Query("SELECT * FROM notes WHERE id = :id")
    suspend fun getNote(id : Long) : Note

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note : Note) : Long

    @Update
    suspend fun update(note : Note) : Int

    @Delete
    suspend fun delete(note : Note)
}