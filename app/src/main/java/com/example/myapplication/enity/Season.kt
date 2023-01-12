package com.example.myapplication.enity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity
class Season(@ColumnInfo(name="user_id")
             var user_id: Int?,
             @ColumnInfo(name="year")
             var year: String?){
            @PrimaryKey(autoGenerate = true)
            var id: Int = 0
}