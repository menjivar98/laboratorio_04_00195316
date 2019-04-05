package com.example.laboratorio_04_00195316.utils

import android.net.Uri
import java.io.IOException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URI
import java.net.URL
import java.util.*

class NetworkUtils {

    val MOVIES_API_URL = "http://www.omdbapi.com/"
    val TOKEN_API = "35f34341"

    fun buildSearchURL(movieName: String):URL{
        val builtURI = Uri.parse(MOVIES_API_URL)
            .buildUpon()
            .appendQueryParameter("apikey",TOKEN_API)
            .appendQueryParameter("t",movieName)
            .build()

        return try {
            URL(builtURI.toString())
        }catch (e : MalformedURLException) {
            URL("")
        }
    }

    @Throws(IOException ::class)
    fun getResponseFromHttpURL(url: URL) : String {
        val urlConnection = url.openConnection() as HttpURLConnection
        try {
            val in = urlConnection.inputStream

            val scanner = Scanner(in)
            scanner.useDelimiter("\\A")

            val hasInput = scanner.hasNext()
            return if(hasInput){
                scanner.next()
            }else{
                ""
            }

        }finally {
            urlConnection.disconnect()
        }
    }
}