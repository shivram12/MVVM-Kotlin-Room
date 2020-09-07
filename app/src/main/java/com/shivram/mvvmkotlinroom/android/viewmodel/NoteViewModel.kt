package com.shivram.mvvmkotlinroom.android.viewmodel

import android.app.Application
import androidx.annotation.NonNull
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.shivram.mvvmkotlinroom.android.model.Note
import com.shivram.mvvmkotlinroom.android.repository.NoteRepository

class NoteViewModel(@NonNull application: Application): AndroidViewModel(application) {
    private val repository: NoteRepository = NoteRepository(application)
    val allNotes: LiveData<List<Note>>
    init{
        allNotes = repository.allNotes
    }
    fun insert(note:Note) {
        repository.insert(note)
    }
    fun update(note:Note) {
        repository.update(note)
    }
    fun delete(note:Note) {
        repository.delete(note)
    }
    fun deleteAllNotes() {
        repository.deleteAllNotes()
    }
}