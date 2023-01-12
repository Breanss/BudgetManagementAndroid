package com.example.myapplication.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.myapplication.enity.Season

@Dao
interface SeasonDao {
    @Insert
    fun insertSeason(season: Season)

    @Query("select * from season where user_id = :userID and year=:year")
    fun findSeasonByUserIdAndYear(userID: Int, year: String):Season

    @Query("select * from season where user_id=:userID")
    fun findSeasonByUserId(userID: Int):List<Season>

    @Query("select * from season where id=:seasonID")
    fun findSeasonBySeasonId(seasonID: Int):Season
}