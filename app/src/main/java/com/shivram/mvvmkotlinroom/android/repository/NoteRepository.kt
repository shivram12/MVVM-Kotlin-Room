package com.shivram.mvvmkotlinroom.android.repository

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.shivram.mvvmkotlinroom.android.dao.NoteDao
import com.shivram.mvvmkotlinroom.android.database.NoteDatabase
import com.shivram.mvvmkotlinroom.android.model.Note

class NoteRepository(application: Application) {
    private val noteDao: NoteDao
    val allNotes: LiveData<List<Note>>
    init{
        val database = NoteDatabase.noteDatabase(application)
        noteDao = database.noteDao()
        allNotes = noteDao.getAllNotes()
    }
    fun insert(note:Note) {
        InsertNoteAsyncTask(noteDao).execute(note)
    }
    fun update(note:Note) {
        UpdateNoteAsyncTask(noteDao).execute(note)
    }
    fun delete(note:Note) {
        DeleteNoteAsyncTask(noteDao).execute(note)
    }
    fun deleteAllNotes() {
        DeleteAllNotesAsyncTask(noteDao).execute()
    }
    private class InsertNoteAsyncTask(noteDao:NoteDao):
        AsyncTask<Note, Void, Void>() {
        private val noteDao:NoteDao = noteDao
        override fun doInBackground(vararg notes:Note): Void? {
            noteDao.insert(notes[0])
            return null
        }
    }
    private class UpdateNoteAsyncTask(noteDao:NoteDao):AsyncTask<Note, Void, Void>() {
        private val noteDao:NoteDao = noteDao
         override fun doInBackground(vararg notes:Note): Void? {
            noteDao.update(notes[0])
            return null
        }
    }
    private class DeleteNoteAsyncTask(noteDao:NoteDao):AsyncTask<Note, Void, Void>() {
        private val noteDao:NoteDao = noteDao
         override fun doInBackground(vararg notes:Note): Void? {
            noteDao.delete(notes[0])
            return null
        }
    }
    private class DeleteAllNotesAsyncTask(noteDao:NoteDao):AsyncTask<Void, Void, Void>() {
        private val noteDao:NoteDao = noteDao
         override fun doInBackground(vararg voids:Void): Void? {
            noteDao.deleteAllNotes()
            return null
        }
    }
}