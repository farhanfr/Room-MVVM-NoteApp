package com.farhanfr.simplenoteapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.farhanfr.simplenoteapp.R
import com.farhanfr.simplenoteapp.room.table.NoteTable
import com.farhanfr.simplenoteapp.room.viewmodel.NoteViewModel


class NoteAdapter(val context: Context): RecyclerView.Adapter<NoteAdapter.ViewHolder>() {
    lateinit var noteViewModel: NoteViewModel
    private var notes:MutableList<NoteTable> = ArrayList()
    private var listener: OnItemClickListener? = null

//    init {
//        db = Room.databaseBuilder(context,NoteDatabase::class.java,"notes_database").build()
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteAdapter.ViewHolder {
        val viewLayout = LayoutInflater.from(parent.context).inflate(R.layout.rc_note_layout,parent,false)
        return ViewHolder(viewLayout)
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    fun setNotes(notes: MutableList<NoteTable>){
        this.notes = notes
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: NoteAdapter.ViewHolder, position: Int) {
        holder.tvTitle.text = notes.get(position).title
        holder.tvDesc.text = notes.get(position).description
        holder.cvDetailNote.setOnClickListener {
            listener?.onItemClick(notes.get(position))

//            val toDetNote = Intent(Intent(context,EditActivity::class.java))
//            toDetNote.putExtra("id",notes.get(position).id)
//            toDetNote.putExtra("titleData",notes.get(position).title)
//            toDetNote.putExtra("descData",notes.get(position).description)
//            context.startActivity(toDetNote)
        }
    }

    fun getNoteAt(position: Int):NoteTable{
        return notes.get(position)
    }

    class ViewHolder(var view: View):RecyclerView.ViewHolder(view){
        var tvTitle:TextView = view.findViewById(R.id.tvTitle)
        var tvDesc:TextView = view.findViewById(R.id.tvDescription)
        var btnDelete:Button = view.findViewById(R.id.btnDeleteNote)
        var cvDetailNote:CardView = view.findViewById(R.id.cvDetNote)
    }

    interface OnItemClickListener {
        fun onItemClick(note: NoteTable)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }
}