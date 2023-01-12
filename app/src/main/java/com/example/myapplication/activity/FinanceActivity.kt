package com.example.myapplication.activity

import android.content.Intent
import android.graphics.PixelFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.print.PrintAttributes.Margins
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.view.ViewGroup.MarginLayoutParams
import android.webkit.WebSettings.ZoomDensity
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.marginTop
import androidx.core.view.updateLayoutParams
import com.example.myapplication.R
import com.example.myapplication.dao.Connection
import org.w3c.dom.Text

class FinanceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finance)
        val vatCheckBox = findViewById<CheckBox>(R.id.vatCheckBox)
        val addButton = findViewById<Button>(R.id.addFinanceButton)
        val seasonTextView = findViewById<TextView>(R.id.seasonText)
        val vatEditText = findViewById<EditText>(R.id.vatEditText)
        val intent = intent
        val loginUserId = intent.getStringExtra("loginUserId")
        val seasonId = intent.getStringExtra("seasonId")
        val connection = Connection(applicationContext).getConnection()
        val user = connection.userDao().getUserById(Integer.parseInt(loginUserId))
        val season = connection.seasonDao().findSeasonBySeasonId(Integer.parseInt(seasonId))
        seasonTextView.setText("Dodawanie finansów w roku "+season.year)
        setTitle("Witaj "+user.name)

        vatCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                vatEditText.visibility= View.VISIBLE
                addButton.updateLayoutParams<ConstraintLayout.LayoutParams> { verticalBias =0.598F }
            }else{
                vatEditText.visibility= View.INVISIBLE
                addButton.updateLayoutParams<ConstraintLayout.LayoutParams> { verticalBias =0.518F }
            }
        }
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