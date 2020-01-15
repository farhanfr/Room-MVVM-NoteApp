package com.farhanfr.simplenoteapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.farhanfr.simplenoteapp.adapter.NoteAdapter
import com.farhanfr.simplenoteapp.room.viewmodel.NoteViewModel
import kotlinx.android.synthetic.main.activity_main.*
import androidx.recyclerview.widget.RecyclerView
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import com.farhanfr.simplenoteapp.room.table.NoteTable
import androidx.core.app.ActivityCompat.startActivityForResult
import android.nfc.NfcAdapter.EXTRA_ID
import android.provider.ContactsContract.CommonDataKinds.Note
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.R.attr.data
import android.R.attr.priority
import android.R.attr.description
import android.util.Log


@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class MainActivity : AppCompatActivity() {

    public lateinit var noteViewModel: NoteViewModel
    lateinit var noteAdapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        noteAdapter = NoteAdapter(this)
        rcNote.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        rcNote.adapter = noteAdapter

        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel::class.java)
        noteViewModel.getAllNotes().observe(this, Observer{ a
             -> noteAdapter.setNotes(a)
        })

        val mIth = ItemTouchHelper(
            object : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT) {
                override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                    return false
                }
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    noteViewModel.delete(noteAdapter.getNoteAt(viewHolder.adapterPosition))
                    Toast.makeText(applicationContext, "Note deleted", Toast.LENGTH_SHORT).show()
                }
            }).attachToRecyclerView(rcNote)

        noteAdapter.setOnItemClickListener(object : NoteAdapter.OnItemClickListener {
            override fun onItemClick(note: NoteTable) {
                val toDetailNote = Intent(Intent(applicationContext,EditActivity::class.java))
                toDetailNote.putExtra(EditActivity.EXTRA_ID,note.id)
                toDetailNote.putExtra(EditActivity.EXTRA_TITLE,note.title)
                toDetailNote.putExtra(EditActivity.EXTRA_DESCRIPTION,note.description)
                startActivityForResult(toDetailNote,1)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_dropdown,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.addNote->{
                val toAddActivity = Intent(Intent(this,AddActivity::class.java))
                startActivity(toAddActivity)
            }
            R.id.deleteAllNote->{
                noteViewModel.deleteAllNotes()
                Toast.makeText(this, "All notes deleted!", Toast.LENGTH_SHORT).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK){
            val id = data!!.getIntExtra(EditActivity.EXTRA_ID,-1)
            if (id == -1){
                Toast.makeText(this, "Note can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }
            val title = data!!.getStringExtra(EditActivity.EXTRA_TITLE)
            val description = data.getStringExtra(EditActivity.EXTRA_DESCRIPTION)
            Log.d("Server",title)
            val note = NoteTable(id,title,description)
            noteViewModel.updateNotes(note)
            Toast.makeText(this, id.toString(), Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "ERROR", Toast.LENGTH_SHORT).show();
        }
    }
}
