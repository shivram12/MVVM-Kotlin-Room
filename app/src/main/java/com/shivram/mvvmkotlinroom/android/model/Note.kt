package com.shivram.mvvmkotlinroom.android.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note_table")
class Note(title:String, description:String, priority:Int) {
    @PrimaryKey(autoGenerate = true)
    var id:Int = 0

    val title:String = title
    val description:String = description
    var priority:Int = 0
    init{
        this.priority = priority
    }
}