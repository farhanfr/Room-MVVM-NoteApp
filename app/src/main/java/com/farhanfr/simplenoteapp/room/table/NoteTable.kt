package com.farhanfr.simplenoteapp.room.table

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes_table")
data class NoteTable (
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") var id: Int? = null,
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "description") var description: String

)