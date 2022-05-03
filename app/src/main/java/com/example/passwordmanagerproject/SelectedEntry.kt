package com.example.passwordmanagerproject

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceFragmentCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.passwordmanagerproject.model.Account
import com.example.passwordmanagerproject.model.AccountInfo
import com.example.passwordmanagerproject.model.UserData
import com.example.passwordmanagerproject.viewmodel.UserDataViewModel
import java.net.PasswordAuthentication

class SelectedEntry : AppCompatActivity() {
    private lateinit var optionsMenu: Menu
    private lateinit var accountTextView: TextView
    private lateinit var usernameTextView: TextView
    private lateinit var passwordTextView: TextView
    var accountUID: Long = 0




    private lateinit var account: Account
    private lateinit var accountInfo: AccountInfo
    private lateinit var userData: UserData
   private val userDataViewModel: UserDataViewModel by lazy {
        ViewModelProvider(this).get(UserDataViewModel::class.java)
    }

    companion object {
        const val EXTRA_ACCOUNT_ID = "com.example.passwordmanagerproject.account_id"
        const val EXTRA_ACCOUNT_NAME = "com.example.passwordmanagerproject.account_name"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.selected_entry)

        accountTextView = findViewById(R.id.account_input_textview)
        usernameTextView = findViewById(R.id.username_input_textview)
        passwordTextView = findViewById(R.id.password_input_textview)
        val breachWarningTexView = findViewById<LinearLayout>(R.id.breached_label)


        //Provides the account name
        accountUID = intent.getLongExtra(EXTRA_ACCOUNT_ID, 0)



        val tempUserData = userDataViewModel.getUserDataLiveData(accountUID)
        tempUserData.observe(this, {userData ->
            if (userData != null) {
                setTitle(userData.acctName)
                accountTextView.text = userData.acctName
                usernameTextView.text = userData.acctID
                passwordTextView.text = userData.acctPassword
            }

            if(userData?.isPwned == true){
                breachWarningTexView.visibility = View.VISIBLE
            }
        })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.selected_entry_appbar, menu)
        optionsMenu = menu!!
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {

            R.id.delete -> {
                userDataViewModel.deleteUserData(accountUID)
                Toast.makeText(this, "Account Deleted", Toast.LENGTH_SHORT).show()
               finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}