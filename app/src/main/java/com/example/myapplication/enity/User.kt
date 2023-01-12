package com.example.myapplication.enity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @ColumnInfo(name="name")
    var name: String?,
    @ColumnInfo(name="currency")
    var currency: String?,
    @ColumnInfo(name="email")
    var email: String?,
    @ColumnInfo(name="password")
    var password: String?) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    }




