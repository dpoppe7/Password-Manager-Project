package com.example.passwordmanagerproject

import android.content.Context
import android.net.Uri
import com.android.volley.Request
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.passwordmanagerproject.model.UserData
import org.json.JSONException
import org.json.JSONObject


const val WEBAPI_BASE_URL = "https://api.pwnedpasswords.com/range/"
const val KEY = "7a1756d3fa99428889c6fce797baedda"
const val TAG = "Fetcher"

class Fetcher (val context: Context) {
    interface onEntryDataReeceivedListener{
        fun onEntryDataReceived(entriesList: List<UserData>)
    }
}