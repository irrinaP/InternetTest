package com.example.internettest

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var myButton: Button = findViewById(R.id.btnHTTP)
        val url = URL("https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=ff49fcd4d4a08aa6aafb6ea3de826464&tags=cat&format=json&nojsoncallback=1")
        myButton.setOnClickListener {
            Thread {
                val connection = url.openConnection() as HttpURLConnection
                try{
                    val data = connection.inputStream.bufferedReader().readText()
                    connection.disconnect()
                    Log.d("Flickr cats", data)
                } catch(e: Exception) {
                    e.printStackTrace()
                    myButton.setBackgroundColor(Color.RED)
                }
            }.start()
        }
        var myOkButton: Button = findViewById(R.id.btnOkHTTP)
        myOkButton.setOnClickListener {
            var okHttpClient: OkHttpClient = OkHttpClient()
            val request: Request = Request.Builder().url(url).build()
            okHttpClient.newCall(request).enqueue(object: Callback {
                override fun onFailure(call: Call?, e: IOException?) {
                }
                override fun onResponse(call: Call?, response: Response?) {
                    val json = response?.body()?.string()
                    Log.i("Flickr OkCats", json.toString())
                }
            })
        }
    }
}