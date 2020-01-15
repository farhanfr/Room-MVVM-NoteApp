package com.farhanfr.simplenoteapp

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.farhanfr.simplenoteapp.room.table.NoteTable
import com.farhanfr.simplenoteapp.room.viewmodel.NoteViewModel
import kotlinx.android.synthetic.main.activity_edit.*
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import kotlinx.android.synthetic.main.activity_add.*
import android.R.attr.priority
import android.R.attr.description
import android.app.Activity
import android.content.Intent
import android.provider.ContactsContract.CommonDataKinds.Note
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T




class EditActivity: AppCompatActivity() {

    companion object{
        val EXTRA_ID = "EXTRA_ID"
        val EXTRA_TITLE = "EXTRA_TITLE"
        val EXTRA_DESCRIPTION = "EXTRA_DESCRIPTION"
    }
    lateinit var noteViewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        val getDataIntent = intent
        if (getDataIntent.hasExtra(EXTRA_ID)){
            //GetDat
            etTitleEdit.setText(intent.getStringExtra(EXTRA_TITLE))
            etDescEdit.setText(intent.getStringExtra(EXTRA_DESCRIPTION))

            btnEdit.setOnClickListener {
                val getTitleEt = etTitleEdit.text.toString()
                val getDescEt = etDescEdit.text.toString()
                Log.d("SERVERRR",getTitleEt)
                editNote(getTitleEt,getDescEt)
            }
        }

    }

    private fun editNote(titleEt: String, descEt: String) {
        if (titleEt.isEmpty() || descEt.isEmpty()){
            return Toast.makeText(this,"Input cant empty",Toast.LENGTH_SHORT).show()
        }
        else{
            val data = Intent()
            data.putExtra(EXTRA_TITLE,titleEt)
            data.putExtra(EXTRA_DESCRIPTION,descEt)

            val id = intent.getIntExtra(EXTRA_ID,-1)
            if (id != -1){
                Log.d("EXTRAID","EXTRA ID EDIT ACTIVITY TIDAK NULL")
                data.putExtra(EXTRA_ID,id)
            }
            setResult(Activity.RESULT_OK,data)
            finish()
        }
    }
}