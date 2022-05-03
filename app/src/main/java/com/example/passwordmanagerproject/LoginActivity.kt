package com.example.passwordmanagerproject

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.system.Os.remove
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.passwordmanagerproject.model.UserData
import com.example.passwordmanagerproject.viewmodel.UserDataViewModel
import androidx.lifecycle.ViewModelProvider

class LoginActivity : AppCompatActivity() {
    private lateinit var prefs: SharedPreferences
    private lateinit var pinView: EditText
    private lateinit var optionsMenu: Menu

    private val userDataViewModel: UserDataViewModel by lazy {
        ViewModelProvider(this).get(UserDataViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)

        //login button
        val button = findViewById<Button>(R.id.login_button)
        button.setOnClickListener {
            pinView = findViewById(R.id.enterLogin)
            val pin = pinView.text.toString()

            if (pin.length != 5) {
                Toast.makeText(applicationContext, R.string.warning_frag, Toast.LENGTH_SHORT).show()
            } else {
                //get key from Shared Preferences file
                //check if key is found in in Shared Preferences
                prefs = getSharedPreferences("login", Context.MODE_PRIVATE)

                val value = prefs.getString("pin", null)

                //If value is null the key doesn't exist in Shared Preferences
                if (value != null) {
                    if (value == pin) {
                        startMain()
                    } else {
                        Toast.makeText(
                            applicationContext,
                            R.string.invalid_login,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

        }

        //if data exists in the app and user has a stored master pin,
        //create button will not display in the Login Activity

        userDataViewModel.userDataLiveDataList.observe(
            this, { userDataList ->
                updateActivity(userDataList)
            }
        )

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.login_appbar_menu, menu)
        optionsMenu = menu!!
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.reset_app -> {
                prefs = getSharedPreferences("login", Context.MODE_PRIVATE)
                userDataViewModel.blowItUp()
                prefs.edit().remove("pin").apply()
                Toast.makeText(this, "App Reset", Toast.LENGTH_SHORT).show()
                finish()
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun updateActivity(entriesList: List<UserData>) {
        //Get create pin button
        val createPinText = findViewById<TextView>(R.id.create_pin_textview)
        val createButton = findViewById<Button>(R.id.create_pin_button)

        if (entriesList.isEmpty()){
            createPinText.visibility = View.VISIBLE
            createButton.visibility = View.VISIBLE

            createButton.setOnClickListener {
                val intent = Intent(this, CreatePINActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun startMain(){
        val mainIntent = Intent(this, MainActivity::class.java)
        startActivity(mainIntent)
        finish()
        Log.d("check", "I got here")
    }

}