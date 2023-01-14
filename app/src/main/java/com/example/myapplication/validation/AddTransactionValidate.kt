package com.example.myapplication.validation

import com.example.myapplication.R

class AddTransactionValidate:Validators() {

    fun amount(text:String) {
        if(canOnlyContainNumber(text)){
            throw RegisterValidationException(R.string.incorrectAmoutFormat.toString())
        }

        if(minimumNumberIsBad(text)){
            throw RegisterValidationException(R.string.amountMustGreater0.toString())
        }
        if(maximumNumberIsBad(text, 999999)){
            throw RegisterValidationException(R.string.amountToLarge.toString())
        }
    }

    fun vat(text: String){
        if(canOnlyContainNumber(text)){
            throw RegisterValidationException("")
        }

        if(minimumNumberIsBad(text)){
            throw RegisterValidationException("")
        }

        if(maximumNumberIsBad(text,100)){
            throw RegisterValidationException("")
        }

    }
}