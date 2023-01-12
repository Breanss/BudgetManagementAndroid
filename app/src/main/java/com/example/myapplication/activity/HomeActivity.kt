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


class HomeActivity: AppCompatActivity() {
    var saved:Bundle? = Bundle()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val numberOfTransaction=0
        saved = savedInstanceState
        val intentGet = intent
        val loginUserId = intentGet.getStringExtra("loginUserId")

        //controls
        val linearLayout= findViewById<LinearLayout>(R.id.linearButton)
        val buttonAdd = findViewById<Button>(R.id.buttonAdd)
        val spinner = findViewById<Spinner>(R.id.spinnerYear)
        val numberTextView = findViewById<TextView>(R.id.numberTextView)

        //database
        val db = Connection(applicationContext).getConnection()
        val user = db.userDao().getUserById(Integer.parseInt(loginUserId))
        val listSeasons = db.seasonDao().findSeasonByUserId(user.id)
        //another
        val adapter = ArrayAdapter.createFromResource(this, R.array.years, com.google.android.material.R.layout.support_simple_spinner_dropdown_item)
        adapter.setDropDownViewResource(com.google.android.material.R.layout.support_simple_spinner_dropdown_item)
        spinner.adapter=adapter

        setCurrency(user)
        setTitle("Witaj "+user.name)
        numberTextView.setText(numberOfTransaction.toString())

        for(season:Season in listSeasons){
            val button = Button(this)

            var sum = 0.00F;
            val listTransaction = db.transactionDao().findTransactionsBySeasonId(season.id)
            for(transaction:Transaction in listTransaction){
                sum+=transaction.amount
            }

            // set layout
            button.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            button.textSize = 17F

            if(sum>0){
                button.setTextColor(Color.GREEN)
            }else if(sum<0){
                button.setTextColor(Color.RED)
            }else{
                button.setTextColor(Color.BLACK)
            }

            button.text = "Sezon "+season.year+"\nPODSUMOWANIE: "+sum+" "+user.currency

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
            val season = db.seasonDao().findSeasonByUserIdAndYear(user.id, spinner.selectedItem.toString())
            var error = false
            try{
                season.id
            }catch (e:java.lang.NullPointerException){
                error = true;
            }

            if(error){
                val s = Season(user.id,spinner.selectedItem.toString())
                db.seasonDao().insertSeason(s)
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
            val intentLogout= Intent(this, LoginActivity::class.java)
            startActivity(intentLogout)
            return true
    }


    fun setCurrency(user: User){
        if(user.currency.equals("Dolar")){
            user.currency="$"
        }else if(user.currency.equals("Euro")){
            user.currency="€"
        }
    }
    override fun onBackPressed() {
        moveTaskToBack(true)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 2) {
            val transactionTextView = findViewById<TextView>(R.id.numberTextView)
            val message = data?.getStringExtra("numberTransaction")
            finish()
            startActivity(getIntent())
            println(message)
        }
    }
}