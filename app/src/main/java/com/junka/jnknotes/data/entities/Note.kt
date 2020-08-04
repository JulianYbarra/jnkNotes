package com.junka.jnknotes.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    @ColumnInfo(name = "title")
    var title: String = "",
    @ColumnInfo(name = "date")
    var date: Date = Date(),
    @ColumnInfo(name = "subtitle")
    var subtitle: String = "",
    @ColumnInfo(name = "body")
    var body: String = "",
    @ColumnInfo(name = "image")
    var image: String = "",
    @ColumnInfo(name = "color")
    var color: Int = 0,
    @ColumnInfo(name = "web_link")
    var webLink: String = "",
    @ColumnInfo(name = "password")
    var password: String = "",
    @ColumnInfo(name = "visible")
    var visible: Boolean = true
)