package com.example.myapplication.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.myapplication.enity.User

@Dao
interface UserDao {
    @Query("Select * from user")
    fun findAll(): List<User>

    @Query("select * from user where email=:email and password=:password")
    fun getUserByEmailAndPassword(email:String,password:String): User

    @Query("select * from user where email=:email")
    fun getUserByEmail(email: String): User
    @Query("Select * from user where id=:id")
    fun getUserById(id:Int):User
    @Insert
    fun insertUser(user: User)

    @Query("DELETE from user")
    fun deleteAll()
}