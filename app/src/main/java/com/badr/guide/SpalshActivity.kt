package com.badr.guide

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

class SpalshActivity : AppCompatActivity() {

    val ads : Ads = Ads()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spalsh)
        jsonads(this)
        ads.adsinit(this)




        val splashThread: Thread = object : Thread() {
            override fun run() {
                try {
                    var waited = 0
                    while (waited < 1000) {
                        sleep(1000)
                        waited += 100
                    }
                } catch (e: InterruptedException) {
                    // do nothing
                } finally {
                    val i = Intent(applicationContext, Privacy::class.java)
                    startActivity(i)
                    finish()
                }
            }
        }
        splashThread.start()


    }


    fun jsonads(context: Context) {

        val adsurl = "https://shipfiletuning.com/ads.json"

        val queue = Volley.newRequestQueue(context)
        val request = JsonObjectRequest(Request.Method.GET, adsurl, null, { response ->

            Log.d("badro","${response}")

            try {

                val jsonArray = response.getJSONArray("ads")
                val jsonObject = jsonArray.getJSONObject(0)
                Ads.max_banner = jsonObject.getString("max_banner");
                Ads.max_inter = jsonObject.getString("max_inter");
                Ads.max_native= jsonObject.getString("max_native");
                Ads.update=jsonObject.getBoolean("update")


            } catch (e: Exception) {
                e.printStackTrace()
            }

            Log.d("fof","${Ads.max_native}")

        }, { error ->
            Toast.makeText(context, "Fail to get response", Toast.LENGTH_SHORT)
                .show()
        })
        queue.add(request)


    }

}
