package com.shivram.mvvmkotlinroom.android.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shivram.mvvmkotlinroom.android.R
import com.shivram.mvvmkotlinroom.android.adapter.NoteAdapter
import com.shivram.mvvmkotlinroom.android.model.Note
import com.shivram.mvvmkotlinroom.android.viewmodel.NoteViewModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var noteViewModel: NoteViewModel
    val ADD_NOTE_REQUEST = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.setHasFixedSize(true)
        val adapter = NoteAdapter()
        recycler_view.adapter = adapter


//        view model of room
        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel::class.java)
        noteViewModel.allNotes.observe(this,
            Observer<List<Note>> {

                adapter.setNotes(it)
            })


//        swipte left
        ItemTouchHelper(object:ItemTouchHelper.SimpleCallback(0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder:RecyclerView.ViewHolder, target:RecyclerView.ViewHolder):Boolean {
                return false
            }
            override fun onSwiped(viewHolder:RecyclerView.ViewHolder, direction:Int) {
                adapter.getNoteAt(viewHolder.adapterPosition)?.let { noteViewModel.delete(it) }
                Toast.makeText(this@MainActivity, "Note deleted", Toast.LENGTH_SHORT).show()
            }
        }).attachToRecyclerView(recycler_view)


//        fab button add
        button_add_note.setOnClickListener {
            val intent = Intent(this@MainActivity, AddNoteActivity::class.java)
            startActivityForResult(intent, ADD_NOTE_REQUEST)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK)
        {
            val title = data?.getStringExtra(AddNoteActivity.EXTRA_TITLE)
            val description = data?.getStringExtra(AddNoteActivity.EXTRA_DESCRIPTION)
            val priority = data?.getIntExtra(AddNoteActivity.EXTRA_PRIORITY, 1)
            val note = title?.let { description?.let { it1 -> priority?.let { it2 ->
                Note(it, it1,
                    it2
                )
            } } }
            note?.let { noteViewModel.insert(it) }
            Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show()
        }
        else
        {
            Toast.makeText(this, "Note not saved", Toast.LENGTH_SHORT).show()
        }
    }

}