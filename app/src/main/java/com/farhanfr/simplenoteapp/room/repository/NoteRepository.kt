package com.farhanfr.simplenoteapp.room.repository

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.farhanfr.simplenoteapp.room.dao.NoteDao
import com.farhanfr.simplenoteapp.room.db.NoteDatabase
import com.farhanfr.simplenoteapp.room.table.NoteTable

class NoteRepository(application: Application) {

    private var noteDao: NoteDao
    private var allNotes: LiveData<MutableList<NoteTable>>

    init {
        val database: NoteDatabase = NoteDatabase.getInstance(
            application.applicationContext
        )!!
        noteDao = database.noteDao()
        allNotes = noteDao.getAllNotes()
    }

    fun insert(note: NoteTable) {
        InsertNoteAsyncTask(
            noteDao
        ).execute(note)
    }

    fun deleteAllNotes() {
        DeleteAllNotesAsyncTask(
            noteDao
        ).execute()
    }

    fun getAllNotes(): LiveData<MutableList<NoteTable>> {
        return allNotes
    }

    fun delete(note: NoteTable){
        DeleteNoteAsyncTask(noteDao).execute(note)
    }

    fun update(note: NoteTable){
        UpdateNoteAsyncTask(noteDao).execute(note)
    }

    private class DeleteNoteAsyncTask(val noteDao: NoteDao): AsyncTask<NoteTable, Unit, Unit>() {
        override fun doInBackground(vararg noteTable: NoteTable?) {
            noteDao.deleteNote(noteTable[0]!!)
        }

    }

    private class InsertNoteAsyncTask(val noteDao: NoteDao): AsyncTask<NoteTable,Unit,Unit>(){
        override fun doInBackground(vararg noteTable: NoteTable?) {
            noteDao.insert(noteTable[0]!!)
        }
    }

    private class DeleteAllNotesAsyncTask(val noteDao: NoteDao):AsyncTask<Unit,Unit,Unit>(){
        override fun doInBackground(vararg p0: Unit?) {
            noteDao.deleteAllNotes()
        }
    }
    class UpdateNoteAsyncTask(val noteDao: NoteDao): AsyncTask<NoteTable, Unit, Unit>() {
        override fun doInBackground(vararg noteTable: NoteTable?) {
            noteDao.updateNote(noteTable[0]!!)
        }

    }

}


