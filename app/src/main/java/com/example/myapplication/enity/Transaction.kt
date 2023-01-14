package com.example.myapplication.enity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Transaction(@ColumnInfo(name="season_id")
                  var season_id: Int?,
                  @ColumnInfo(name="amount")
                  var amount: Float){
                  @PrimaryKey(autoGenerate = true)
                  var id: Int = 0
}