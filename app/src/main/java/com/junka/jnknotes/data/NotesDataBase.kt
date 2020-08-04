package com.junka.jnknotes.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.junka.jnknotes.data.dao.NoteDao
import com.junka.jnknotes.data.entities.Note
import android.content.Context
import androidx.room.Room

@Database(entities = [Note::class], version = 1, exportSchema = false)
@TypeConverters(DateConverters::class)
abstract class NotesDataBase : RoomDatabase() {
    abstract val noteDao: NoteDao

    companion object{

        fun buildDatabase(context : Context) : NotesDataBase {
            return Room.databaseBuilder(context, NotesDataBase::class.java, "notes_database").build()
        }
    }
}
