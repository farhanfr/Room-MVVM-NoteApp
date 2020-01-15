package com.farhanfr.simplenoteapp.room.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.farhanfr.simplenoteapp.room.table.NoteTable

@Dao
interface NoteDao {
    @Insert
    fun insert(noteTable: NoteTable)

    @Delete
    fun deleteNote(noteTable: NoteTable)

    @Update
    fun updateNote(noteTable: NoteTable)

//    @Query("UPDATE notes_table SET title = :title, de  ")
//    fun updateNote2(
//         title:String
//    )

    @Query("DELETE FROM notes_table")
    fun deleteAllNotes()

    @Query("SELECT * FROM notes_table ")
    fun getAllNotes(): LiveData<MutableList<NoteTable>>
}