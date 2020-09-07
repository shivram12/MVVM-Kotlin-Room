package com.shivram.mvvmkotlinroom.android.database

import android.content.Context
import android.os.AsyncTask
import androidx.annotation.NonNull
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.shivram.mvvmkotlinroom.android.dao.NoteDao
import com.shivram.mvvmkotlinroom.android.model.Note

@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class NoteDatabase: RoomDatabase() {
    abstract fun noteDao(): NoteDao
    companion object {

        @Volatile
        private var INSTANCE: NoteDatabase? = null

        fun noteDatabase(context: Context) : NoteDatabase {

            if (INSTANCE != null) return INSTANCE!!

            synchronized(this) {

                INSTANCE = Room
                    .databaseBuilder(context, NoteDatabase::class.java, "note.db")
                    .fallbackToDestructiveMigration()
                    .build()

                return INSTANCE!!

            }
        }

    }

    private val roomCallback = object:RoomDatabase.Callback() {
        override fun onCreate(@NonNull db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { PopulateDbAsyncTask(it).execute() }
        }
    }

    private class PopulateDbAsyncTask(db:NoteDatabase): AsyncTask<Void, Void, Void>() {
        private val noteDao:NoteDao = db.noteDao()
        protected override fun doInBackground(vararg voids:Void): Void? {
            noteDao.insert(Note("Title 1", "Description 1", 1))
            noteDao.insert(Note("Title 2", "Description 2", 2))
            noteDao.insert(Note("Title 3", "Description 3", 3))
            return null
        }
    }
}