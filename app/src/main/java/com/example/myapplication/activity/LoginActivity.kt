package com.example.myapplication.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.myapplication.R
import com.example.myapplication.dao.Connection


class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //controls
        val buttonLogin = findViewById<Button>(R.id.loginButton)
        val emailEditText = findViewById<EditText>(R.id.emailEditText)
        val passwordEditText = findViewById<EditText>(R.id.passwordEditText)
        val buttonChangeOnRegister = findViewById<TextView>(R.id.buttonChangeOnRegister)
        //database
        val db = Connection(applicationContext).getConnection()

        buttonChangeOnRegister.setOnClickListener {
            val intentRegister = Intent(this, RegisterActivity::class.java)
            startActivity(intentRegister)
        }

        buttonLogin.setOnClickListener{

            val user = db.userDao().getUserByEmailAndPassword(emailEditText.text.toString(),passwordEditText.text.toString())
            if(user!=null){
                val intentLogin = Intent(this, HomeActivity::class.java)
                intentLogin.putExtra("loginUserId", user.id.toString())
                startActivity(intentLogin)
            }else{
                emailEditText.setText("")
                passwordEditText.setText("")
                Toast.makeText(this@LoginActivity, R.string.incorrectLoginOrPassword, Toast.LENGTH_SHORT).show()
            }

        }

    }

}


