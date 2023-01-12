package com.example.myapplication.activity

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.dao.Connection
import com.example.myapplication.enity.User
import com.example.myapplication.validation.RegisterValidate


class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        //controls
        val nameEditText = findViewById<EditText>(R.id.nameEditText)
        val emailEditText = findViewById<EditText>(R.id.emailEditText)
        val passwordEditText = findViewById<EditText>(R.id.passwordEditText)
        val registerButton = findViewById<Button>(R.id.registerButton)
        val textViewErrorEmail = findViewById<TextView>(R.id.textViewErrorEmail)
        val textViewErrorPassword = findViewById<TextView>(R.id.textViewErrorPassword)
        val textViewErrorName = findViewById<TextView>(R.id.textViewErrorName)
        val buttonChangeOnLogin = findViewById<TextView>(R.id.buttonChangeOnLogin)
        val spinner = findViewById<Spinner>(R.id.spinner)
        //database
        val connection = Connection(applicationContext).getConnection()
        //another
        val registerValidate = RegisterValidate()
        val adapter = ArrayAdapter.createFromResource(this, R.array.currency, com.google.android.material.R.layout.support_simple_spinner_dropdown_item)

        adapter.setDropDownViewResource(com.google.android.material.R.layout.support_simple_spinner_dropdown_item)
        spinner.adapter=adapter

        buttonChangeOnLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        registerButton.setOnClickListener {
            var error = false
            textViewErrorEmail.setText("")

            try {
                registerValidate.password(passwordEditText.text.toString())
            } catch (e: Exception) {
                error = true
                textViewErrorPassword.setText(getString(Integer.parseInt(e.message)))
            }

            try {
                registerValidate.email(emailEditText.text.toString(), applicationContext)
            } catch (e: Exception) {
                error = true
                textViewErrorEmail.setText(getString(Integer.parseInt(e.message)))
            }

            try {
                registerValidate.name(nameEditText.text.toString())
            } catch (e: Exception) {
                error = true
                textViewErrorName.setText(getString(Integer.parseInt(e.message)))
            }

            if (!error) {
                val user = User(
                    nameEditText.text.toString(),
                    spinner.selectedItem.toString(),
                    emailEditText.text.toString(),
                    passwordEditText.text.toString()
                )
                connection.userDao().insertUser(user)
                Toast.makeText(this@RegisterActivity, R.string.registerSuccess, Toast.LENGTH_SHORT)
                    .show()
            }

            nameEditText.setText("")
            passwordEditText.setText("")
            emailEditText.setText("")
        }
    }
}