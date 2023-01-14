package com.example.myapplication.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.dao.Connection
import com.example.myapplication.enity.Season
import com.example.myapplication.enity.Transaction
import com.example.myapplication.enity.User
import kotlin.math.roundToInt


class HomeActivity : AppCompatActivity() {

    private var ile = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)


        //intentGet
        val intentGet = intent
        val loginUserId = intentGet.getStringExtra("loginUserId")
        val number = intentGet.getStringExtra("numberTransac")


        //controls
        val linearLayout = findViewById<LinearLayout>(R.id.linearButton)
        val buttonAdd = findViewById<Button>(R.id.buttonAdd)
        val spinner = findViewById<Spinner>(R.id.spinnerYear)
        val numberTextView = findViewById<TextView>(R.id.numberTextView)


        //database
        val dbConnection = Connection(applicationContext).getConnection()
        val user = dbConnection.userDao().getUserById(Integer.parseInt(loginUserId.toString()))
        val listSeasons = dbConnection.seasonDao().findSeasonByUserId(user.id)

        //another
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.years,
            com.google.android.material.R.layout.support_simple_spinner_dropdown_item
        )
        adapter.setDropDownViewResource(com.google.android.material.R.layout.support_simple_spinner_dropdown_item)
        spinner.adapter = adapter

        setCurrency(user)
        title = getString(R.string.welcome)+" "+ user.name

        if (number != null) {
            numberTextView.text = number
            ile = Integer.parseInt(number.toString())
        } else
            numberTextView.text = "0"

        for (season: Season in listSeasons) {
            val button = Button(this)

            var sum = 0.00F
            val listTransaction = dbConnection.transactionDao().findTransactionsBySeasonId(season.id)
            for (transaction: Transaction in listTransaction) {
                sum += transaction.amount
            }
            sum=(sum*100.0).roundToInt()/100.0F

            // set layout
            button.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            button.textSize = 17F

            if (sum > 0) {
                button.setTextColor(Color.rgb(37,107,0))
            } else if (sum < 0) {
                button.setTextColor(Color.RED)
            } else {
                button.setTextColor(Color.BLACK)
            }

            button.text = getString(R.string.season)+" "+ season.year + "\n"+getString(R.string.summary)+" " + sum + " " + user.currency

            // add Button to LinearLayout
            linearLayout.addView(button)

            button.setOnClickListener {
                val i = Intent(this, FinanceActivity::class.java)
                i.putExtra("loginUserId", user.id.toString())
                i.putExtra("seasonId", season.id.toString())
                startActivityForResult(i, 2)

            }
        }

        buttonAdd.setOnClickListener {
            val season =
                dbConnection.seasonDao().findSeasonByUserIdAndYear(user.id, spinner.selectedItem.toString())
            var error = false
            try {
                season.id
            } catch (e: java.lang.NullPointerException) {
                error = true;
            }

            if (error) {
                val s = Season(user.id, spinner.selectedItem.toString())
                dbConnection.seasonDao().insertSeason(s)
                finish()
                overridePendingTransition(0, 0)
                val reload = Intent(this, HomeActivity::class.java)
                reload.putExtra("loginUserId", user.id.toString())
                startActivity(intent)
                overridePendingTransition(0, 0)
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val intentLogout = Intent(this, LoginActivity::class.java)
        startActivity(intentLogout)
        return true
    }


    private fun setCurrency(user: User) {
        if (user.currency.equals("Dolar")) {
            user.currency = "$"
        } else if (user.currency.equals("Euro")) {
            user.currency = "€"
        }
    }

    override fun onBackPressed() {
        moveTaskToBack(true)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 2) {
            val message = data?.getStringExtra("numberTransaction")
            val tmp = ile + Integer.parseInt(message.toString())
            startActivity(intent.putExtra("numberTransac", tmp.toString()))
        }
    }
}