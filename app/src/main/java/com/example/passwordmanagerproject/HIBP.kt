package com.example.passwordmanagerproject


import android.content.Context
import android.net.Uri
import android.util.Log
import com.android.volley.Request
import com.android.volley.VolleyError
import com.android.volley.toolbox.Volley
import com.android.volley.toolbox.*


const val WEBAPI_URL = "https://api.pwnedpasswords.com/range/"
const val API_KEY = "7a1756d3fa99428889c6fce797baedda"


class HIBP (context: Context){


    interface OnSuffixReceivedListener {
        fun onSuffixReceived(suffixList: String){}
        fun onErrorResponse(error: VolleyError){}

    }
    var wasPwned = false
    private val hashUtils = HashUtils
    private var requestQueue = Volley.newRequestQueue(context)
    var suffixList= Array(0){""}
    var prefix = ""
    var suffix = ""

    fun testPassword(password: String, listener: OnSuffixReceivedListener){
        wasPwned = false
        prefix = password.subSequence(0,5).toString()
        suffix = password.subSequence(5, 40).toString()
        fetchHashes(prefix,listener)
    }

    private fun fetchHashes(prefix: String, listener: OnSuffixReceivedListener) {
        val url = Uri.parse(WEBAPI_URL).buildUpon()
            .appendPath(prefix)
            .build().toString()

        val stringReq = StringRequest(Request.Method.GET, url,
            { response ->
                listener.onSuffixReceived(response.toString())
            },
            { error -> Log.d("sup", error.toString()) })

        requestQueue.add(stringReq)
    }

    fun isPwned(): Boolean{
        var counter = 0;
        while (counter in suffixList.indices && !wasPwned)
        {
            if(suffix == suffixList[counter])
                wasPwned = true
            counter++
        }
        return wasPwned
    }

    fun cleanSuffixes(suffixList: Array<String>): Array<String>
    {
        val tempArray = suffixList
        for(i in suffixList.indices)
        {
            tempArray[i] = suffixList[i].subSequence(0,35).toString()
        }
        return tempArray
    }

}