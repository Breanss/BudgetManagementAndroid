package com.example.myapplication.validation
import android.content.Context
import  com.example.myapplication.R


class RegisterValidate():Validators(){
    fun password(text:String){
        if(minimumLengthIsBad(text,6)){
            throw RegisterValidationException(R.string.passwordShuldContainLeast6.toString())
        }
        if(canNotOnlyContainLetters(text)){
            throw RegisterValidationException(R.string.passwordCannotContainOnlyLetters.toString())
        }
        if(maximumLengthIsBad(text, 40)){
            throw RegisterValidationException(R.string.passwordShuldNotMoreThan40.toString())
        }
    }

    fun email(text: String, context: Context){
        if(incorrectFormatEmail(text)){
            throw RegisterValidationException(R.string.invalidEmailFormat.toString())
        }

        if(canNotTheSameEmail(text, context)){
            throw RegisterValidationException(R.string.givenEmailIsAlreadyInUse.toString())
        }
        if(maximumLengthIsBad(text, 40)){
            throw RegisterValidationException(R.string.passwordShuldNotMoreThan40.toString())
        }
    }

    fun name(text: String){
        if(minimumLengthIsBad(text,3)){
            throw RegisterValidationException(R.string.firstNameShuldContainLeast3.toString())
        }
        if(canOnlyContainLetters(text)) {
            throw RegisterValidationException(R.string.firstNameCanOnlyContainLetters.toString())
        }
    }
}