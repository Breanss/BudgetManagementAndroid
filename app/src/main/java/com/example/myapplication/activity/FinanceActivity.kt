package com.example.myapplication.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams
import com.example.myapplication.R
import com.example.myapplication.dao.Connection
import com.example.myapplication.enity.Transaction
import com.example.myapplication.validation.AddTransactionValidate

class FinanceActivity : AppCompatActivity() {

    private var numberTransaction = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finance)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //intentGet
        val intent = intent
        val loginUserId = intent.getStringExtra("loginUserId")
        val seasonId = intent.getStringExtra("seasonId")

        //controls
        val vatCheckBox = findViewById<CheckBox>(R.id.vatCheckBox)
        val addButton = findViewById<Button>(R.id.addFinanceButton)
        val seasonTextView = findViewById<TextView>(R.id.seasonText)
        val vatEditText = findViewById<EditText>(R.id.vatEditText)
        val amountEditText = findViewById<EditText>(R.id.amountEditText)
        val spinnerAction = findViewById<Spinner>(R.id.spinnerAction)
        val errorAmountEditText = findViewById<TextView>(R.id.errorAmountEditText)

        //database
        val connection = Connection(applicationContext).getConnection()
        val user = connection.userDao().getUserById(Integer.parseInt(loginUserId))
        val season = connection.seasonDao().findSeasonBySeasonId(Integer.parseInt(seasonId.toString()))

        //another
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.action,
            com.google.android.material.R.layout.support_simple_spinner_dropdown_item
        )
        adapter.setDropDownViewResource(com.google.android.material.R.layout.support_simple_spinner_dropdown_item)
        spinnerAction.adapter = adapter
        seasonTextView.text =getString(R.string.addingMoneyYear) + " "+season.year
        title = getString(R.string.welcome)+" "+user.name


        addButton.setOnClickListener {
            var error = false
            val addTransactionValidate=AddTransactionValidate()

            try{
                addTransactionValidate.amount(amountEditText.text.toString())
            }catch (e:Exception){
                errorAmountEditText.text=getString(Integer.parseInt(e.message.toString()))
                error=true
            }

            if(vatCheckBox.isChecked){
                try{
                    addTransactionValidate.vat(vatEditText.text.toString())
                }catch (e:Exception){
                    error=true
                }
            }

            if(!error) {
                var tmp = amountEditText.text.toString().toFloat()

                if (spinnerAction.selectedItem.equals("-")) {
                    tmp *= (-1)
                }
                if (vatCheckBox.isChecked) {
                    if(spinnerAction.selectedItem.equals("-"))
                        tmp += tmp * ((vatEditText.text.toString().toFloat()) / 100)
                    else
                        tmp -= tmp * ((vatEditText.text.toString().toFloat()) / 100)
                    vatEditText.setText("")
                }
                amountEditText.setText("")
                val transaction = Transaction(season.id, tmp)
                connection.transactionDao().insertTransaction(transaction)
                numberTransaction++
            }
        }

        vatCheckBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                vatEditText.visibility= View.VISIBLE
                addButton.updateLayoutParams<ConstraintLayout.LayoutParams> { verticalBias =0.672F }
            }else{
                vatEditText.visibility= View.INVISIBLE
                addButton.updateLayoutParams<ConstraintLayout.LayoutParams> { verticalBias =0.609F }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                val i = Intent(this, HomeActivity::class.java)
                i.putExtra("numberTransaction", numberTransaction.toString())
                setResult(RESULT_OK, i)
                finish()
            }
            R.id.logout->{
                val intentLogout = Intent(this, LoginActivity::class.java)
                startActivity(intentLogout)
                return true
            }

        }
        return true
    }

    override fun onBackPressed() {
        val i = Intent(this, HomeActivity::class.java)
        i.putExtra("numberTransaction", numberTransaction.toString())
        setResult(RESULT_OK, i)
        finish()
    }


}