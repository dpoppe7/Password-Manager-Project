package com.example.passwordmanagerproject

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.preference.PreferenceManager
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.passwordmanagerproject.model.UserData
import com.example.passwordmanagerproject.viewmodel.UserDataViewModel


class MainActivity : AppCompatActivity() {
    private lateinit var optionsMenu: Menu
    private lateinit var showEntriesLayout: ViewGroup
    private lateinit var noEntriesLayout: ViewGroup
    private lateinit var entryRecyclerView: RecyclerView
    private var entryAdapter = Adapter(mutableListOf())
    private val userDataViewModel: UserDataViewModel by lazy {
        ViewModelProvider(this).get(UserDataViewModel::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showEntriesLayout = findViewById(R.id.show_entries_layout)
        noEntriesLayout = findViewById(R.id.no_entries_layout)

        entryRecyclerView = findViewById(R.id.entry_recycler_view)
        entryRecyclerView.layoutManager = LinearLayoutManager(applicationContext)



        userDataViewModel.userDataLiveDataList.observe(
            this, { userDataList ->
                updateActivity(userDataList)
            }
        )





        // Show the entries
        //updateActivity(userDataViewModel.userDataList)
    }

    override fun onStart() {
        super.onStart()

        val defPref = getSharedPreferences("login", Context.MODE_PRIVATE)
        val password = defPref.getString("pin", "")
        if (password!!.isEmpty()) {
            startActivity(Intent(this@MainActivity, LoginActivity::class.java))
            Log.d("check", "made it to main")
            finish()
        }
    }


    private fun updateActivity(entriesList: List<UserData>) {
        if (entriesList.isEmpty()){
            showEntriesLayout.visibility = View.GONE
            noEntriesLayout.visibility = View.VISIBLE
        }
        else {
            showEntriesLayout.visibility = View.VISIBLE
            noEntriesLayout.visibility = View.GONE
            entryAdapter = Adapter(entriesList as MutableList<UserData>)
            entryRecyclerView.adapter = entryAdapter
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_appbar_menu, menu)
        optionsMenu = menu!!
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.settings -> {
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.add -> {
                val intent = Intent(this, AddEntry::class.java)
                startActivity(intent)
                true
            }
            R.id.about -> {
                val intent = Intent(this, AboutAppActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.blow_it_up ->
            {
                userDataViewModel.blowItUp()
                finish()
                startActivity(getIntent())
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private inner class EntryHolder(inflater: LayoutInflater, parent: ViewGroup?) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.recycler_view_items, parent, false)),
        View.OnClickListener {

        private var userData: UserData? = null
        private val userDataTextView: TextView
        //private val isPwnedTextView: TextView
        private var isPwnedIcon: ImageView
        init {
            itemView.setOnClickListener(this)
            userDataTextView = itemView.findViewById(R.id.entry_text_view)
            //isPwnedTextView = itemView.findViewById(R.id.entry_ispwned_text_view)
            isPwnedIcon = itemView.findViewById(R.id.is_pwned_icon)
        }

        fun bind(userData: UserData, position: Int) {
            this.userData = userData
            userDataTextView.text = userData.acctName

            if(userData.isPwned){
                //isPwnedTextView.text = getString(R.string.breach_found)
                isPwnedIcon.setImageDrawable(getDrawable(R.drawable.is_pwned_icon))
            }

            // Background color
            userDataTextView.setBackgroundColor(Color.DKGRAY)
            isPwnedIcon.setBackgroundColor(Color.DKGRAY)
        }

        override fun onClick(view: View) {
            val intent = Intent(this@MainActivity, SelectedEntry::class.java)
            intent.putExtra(SelectedEntry.EXTRA_ACCOUNT_ID, userData!!.id)
            intent.putExtra(SelectedEntry.EXTRA_ACCOUNT_NAME, userData!!.acctName)

            startActivity(intent)
        }

    }


    private inner class Adapter(private val entriesList: MutableList<UserData>) :
        RecyclerView.Adapter<EntryHolder>(){

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntryHolder {
            val layoutInflater = LayoutInflater.from(applicationContext)
            return EntryHolder(layoutInflater, parent)
        }

        override fun onBindViewHolder(holder: EntryHolder, position: Int) {
            holder.bind(entriesList[position], position)
        }

        override fun getItemCount(): Int {
            return entriesList.size
        }
    }
}