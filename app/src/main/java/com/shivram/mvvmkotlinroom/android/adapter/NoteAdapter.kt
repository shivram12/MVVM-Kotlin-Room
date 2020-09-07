package com.shivram.mvvmkotlinroom.android.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.shivram.mvvmkotlinroom.android.R
import com.shivram.mvvmkotlinroom.android.model.Note

 class NoteAdapter: RecyclerView.Adapter<NoteAdapter.NoteHolder>() {
    private  var notes : List<Note> = ArrayList()

    @NonNull
    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType:Int):NoteHolder {
        val itemView = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.note_item, parent, false)
        return NoteHolder(itemView)
    }
    override fun onBindViewHolder(@NonNull holder:NoteHolder, position:Int) {
        val currentNote = notes[position]
        holder.textViewTitle.text = currentNote.title
        holder.textViewDescription.text = currentNote.description
        holder.textViewPriority.text = (currentNote.priority).toString()
    }
    fun setNotes(notes:List<Note>) {
        this.notes = notes
        notifyDataSetChanged()
    }

     fun getNoteAt(position: Int): Note? {
         return notes[position]
     }
    inner class NoteHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val textViewTitle: TextView = itemView.findViewById(R.id.text_view_title)
        val textViewDescription:TextView = itemView.findViewById(R.id.text_view_description)
        val textViewPriority:TextView = itemView.findViewById(R.id.text_view_priority)
    }

    override fun getItemCount(): Int {
        return notes.size
    }
}