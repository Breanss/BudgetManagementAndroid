package com.example.myapplication.validation

import android.content.Context
import com.example.myapplication.dao.Connection
import java.util.regex.Pattern

abstract class Validators {
    fun minimumLengthIsBad(text:String, minLength:Int):Boolean{
        if(text==null)
            return true
        return text.length<minLength
    }

    fun maximumLengthIsBad(text:String, maxLength:Int):Boolean{
        return text.length>maxLength
    }

    fun canNotOnlyContainLetters(text: String):Boolean{
        val pattern = Pattern.compile("[A-Za-zĄąĘęÓóŚśŁłŹźŻżĆćŃń]+")
        return pattern.matcher(text).matches()
    }

    fun canOnlyContainLetters(text: String):Boolean{
        val pattern = Pattern.compile("[A-Za-z]+")
        return !pattern.matcher(text).matches()
    }

    fun canOnlyContainNumber(text: String):Boolean{
        val pattern = Pattern.compile("[0-9.]+")
        return !pattern.matcher(text).matches()
    }

    fun minimumNumberIsBad(text: String):Boolean{
        if(text==null){
            return true
        }
        return text.toFloat()<=0
    }

    fun maximumNumberIsBad(text: String, number:Int):Boolean{
        return text.toFloat()>number
    }

    fun incorrectFormatEmail(text: String):Boolean{
        val pattern = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$")
        return !pattern.matcher(text).matches()
    }

    fun canNotTheSameEmail(text: String, context: Context):Boolean{
        val connection = Connection(context)
        val user = connection.getConnection().userDao().getUserByEmail(text)
        var tmp = true
        try{
            user.email
        }catch (e:java.lang.NullPointerException){
            tmp=false
        }
        return  tmp
    }
}