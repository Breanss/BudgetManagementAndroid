package com.example.myapplication.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import com.example.myapplication.R
import com.example.myapplication.dao.Connection

class FinanceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finance)

        val intent = intent
        val loginUserId = intent.getStringExtra("loginUserId")
        val connection = Connection(applicationContext).getConnection()
        val user = connection.userDao().getUserById(Integer.parseInt(loginUserId))
        setTitle("Witaj "+user.name)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val intentLogout= Intent(this, LoginActivity::class.java)
        startActivity(intentLogout)
        return true
    }
}