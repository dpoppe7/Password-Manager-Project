package com.example.passwordmanagerproject

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class CreatePINActivity : AppCompatActivity() {

    lateinit var pin1 : EditText
    lateinit var pin2 : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_pin_activity)

        pin1 = findViewById(R.id.create_pin_edit_text)
        pin2 = findViewById(R.id.pin_confirmation_edit_text)

        val button = findViewById<Button>(R.id.create_pin_button)
        button.setOnClickListener{

            checkPIN()
        }

    }

    //check to see if PIN is 5 num long and that the second editText matches the first
    private fun checkPIN(){
        val pin1 = findViewById<EditText>(R.id.create_pin_edit_text)
        val pin2 = findViewById<EditText>(R.id.pin_confirmation_edit_text)
        val pinLength = pin1.text.toString().length


//        first check if 5 numbers long then check if ->
//            cross check the edit texts to see if they are the same else ->
        if (pinLength != 5){
            Toast.makeText(applicationContext, R.string.warning_frag, Toast.LENGTH_SHORT).show()
        }
        else {
            if (pin1.text.toString() != pin2.text.toString()) {
                Toast.makeText(applicationContext, R.string.no_match, Toast.LENGTH_SHORT).show()
            }
            else {
                //save PIN for later login
                val pref = getSharedPreferences("login", Context.MODE_PRIVATE)
                val editor = pref.edit()
                editor.putString("pin", pin1.text.toString())
                editor.apply()

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }

}