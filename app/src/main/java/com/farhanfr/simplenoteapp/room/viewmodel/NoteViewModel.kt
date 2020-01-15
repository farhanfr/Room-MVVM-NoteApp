package com.farhanfr.simplenoteapp.room.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.farhanfr.simplenoteapp.room.repository.NoteRepository
import com.farhanfr.simplenoteapp.room.table.NoteTable

class NoteViewModel(application: Application): AndroidViewModel(application) {
    private var repository = NoteRepository(application)
    private var allNotes: LiveData<MutableList<NoteTable>> = repository.getAllNotes()

    fun insert(noteTable: NoteTable){
        repository.insert(noteTable)
    }

    fun getAllNotes():LiveData<MutableList<NoteTable>>{
        return allNotes
    }

    fun delete(noteTable: NoteTable){
        repository.delete(noteTable)
    }

    fun deleteAllNotes(){
        repository.deleteAllNotes()
    }

    fun updateNotes(noteTable: NoteTable){
        repository.update(noteTable)
    }


}