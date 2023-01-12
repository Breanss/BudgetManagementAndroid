package com.example.myapplication.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myapplication.enity.Season
import com.example.myapplication.enity.Transaction
import com.example.myapplication.enity.User

@Database(entities = arrayOf(User::class,Season::class,Transaction::class), version = 1)
abstract class DataBase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun seasonDao(): SeasonDao
    abstract fun transactionDao():TransactionDao
}