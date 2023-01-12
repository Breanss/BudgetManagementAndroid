package com.example.myapplication.dao

import android.content.Context
import androidx.room.Room

class Connection(private val context:  Context) {
    fun getConnection():DataBase{
        val db = Room.databaseBuilder(context, DataBase::class.java, "finance_management").allowMainThreadQueries().build()
        return db;
    }

}