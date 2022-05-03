package com.example.passwordmanagerproject

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.android.volley.VolleyError
import com.example.passwordmanagerproject.HashUtils.sha1
import com.example.passwordmanagerproject.model.UserData
import com.example.passwordmanagerproject.viewmodel.UserDataViewModel

class AddEntry : AppCompatActivity() {

    private lateinit var passwordTextBox: EditText
    private lateinit var usernameTextBox: EditText
    private lateinit var accountTextBox: EditText
    private lateinit var addButton: Button
    private lateinit var haveIbeenPwned: HIBP
    private val userDataViewModel: UserDataViewModel by lazy {
        ViewModelProvider(this).get(UserDataViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_entry_activity)
        haveIbeenPwned = HIBP(this)
        passwordTextBox = findViewById(R.id.password_editText)
        usernameTextBox = findViewById(R.id.username_editText)
        accountTextBox = findViewById(R.id.account_editText)

        addButton = findViewById(R.id.add_account_button)
        addButton.setOnClickListener{
            onAddButtonClick(it)
        }
    }


    private val onSuffixReceivedListener = object: HIBP.OnSuffixReceivedListener {
        override fun onSuffixReceived(suffixList: String) {

            //taken from https://stackoverflow.com/questions/46038476/how-could-i-split-a-string-into-an-array-in-kotlin
            val suffixArray = suffixList.split("\r\n").toTypedArray()

            haveIbeenPwned.suffixList = haveIbeenPwned.cleanSuffixes(suffixArray)
            haveIbeenPwned.isPwned()

            userDataViewModel.addUserData(
                UserData(
                    acctName= accountTextBox.text.toString(),
                    acctPassword = passwordTextBox.text.toString(),
                    acctID = usernameTextBox.text.toString(),
                    isPwned = haveIbeenPwned.wasPwned
            ))
            Toast.makeText(this@AddEntry, "Addition Successful", Toast.LENGTH_SHORT).show()
            finish()
        }

        override fun onErrorResponse(error: VolleyError) {
            Toast.makeText(this@AddEntry, "Request Unsuccessful. Try Again Later", Toast.LENGTH_SHORT).show()
        }
    }
    fun onAddButtonClick(view: View) {
        haveIbeenPwned.testPassword(sha1(passwordTextBox.text.toString()), onSuffixReceivedListener)
    }
}