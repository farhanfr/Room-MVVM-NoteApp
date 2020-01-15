package com.farhanfr.simplenoteapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.farhanfr.simplenoteapp.room.table.NoteTable
import com.farhanfr.simplenoteapp.room.viewmodel.NoteViewModel
import kotlinx.android.synthetic.main.activity_add.*

class AddActivity : AppCompatActivity() {

    lateinit var noteViewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        btnAddNote.setOnClickListener {
            val getTitle = etTitle.text.toString()
            val getDesc = etDesc.text.toString()
            insertNote(getTitle,getDesc)
        }
    }

    private fun insertNote(title: String, desc: String) {
        if (title.isEmpty() || desc.isEmpty()){
            return Toast.makeText(this,"Input cant empty",Toast.LENGTH_SHORT).show()
        }else{
            val addNoteTableData = NoteTable(null,title,desc)
            noteViewModel = ViewModelProviders.of(this).get(NoteViewModel::class.java)
            noteViewModel.insert(addNoteTableData)
            etTitle.text.clear()
            etDesc.text.clear()
            Toast.makeText(this,"Success Add Note",Toast.LENGTH_SHORT).show()
        }

    }


}
