package com.example.myapplication.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.myapplication.enity.Transaction

@Dao
interface TransactionDao {
    @Query("select * from 'transaction' where season_id = :seasonID")
    fun findSeasonByUserIdAndYear(seasonID: Int): List<Transaction>

}