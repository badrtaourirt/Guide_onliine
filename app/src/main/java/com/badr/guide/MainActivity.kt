package com.badr.guide


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.badr.guide.Ads.Companion.interstitialAd
import com.badr.guide.`interface`.OnItemClickListener
import com.badr.guide.adapter.MainReyclerView
import com.badr.guide.model.Model
import com.suddenh4x.ratingdialog.AppRating
import com.suddenh4x.ratingdialog.preferences.RatingThreshold


class MainActivity : AppCompatActivity(), OnItemClickListener {

companion object{

    var description : String = ""
    var  img : String = ""
    var guide_data = "passed_data"
    val url = "https://shipfiletuning.com/content.json"


}


        lateinit var showdata: RecyclerView
        var ads: Ads = Ads()
        lateinit var homemain : ImageView
        lateinit var sharemain : ImageView
        lateinit var rate : ImageView
        var guideList: ArrayList<Model> = ArrayList()

        private val mainReyclerView: MainReyclerView by lazy {
            MainReyclerView(applicationContext, this).apply {
            }
        }


        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)

            json(this)
            showdata = findViewById(R.id.showdata)
            showdata.adapter = mainReyclerView
            showdata.startLayoutAnimation()
            ads.adsinit(this)
            ads.createBannerAd(this)
            interstitialAd.loadAd()
            homemain = findViewById(R.id.homemain)
            sharemain = findViewById(R.id.sharemain)
            rate=findViewById(R.id.rate)
            AppRating.Builder(this)
                .setMinimumLaunchTimes(10)
                .setMinimumDays(0)
                .setMinimumLaunchTimesToShowAgain(5)
                .setMinimumDaysToShowAgain(10)
                .setRatingThreshold(RatingThreshold.FOUR)
                .showIfMeetsConditions()
            homemain.setOnClickListener(){

                val intent=Intent(applicationContext,MainActivity::class.java)
                startActivity(intent)
            }

            sharemain.setOnClickListener(){
                val intent= Intent()
                intent.action=Intent.ACTION_SEND
                intent.putExtra(Intent.EXTRA_TEXT,"Hey Check out this Great app:")
                intent.type="text/plain"
                startActivity(Intent.createChooser(intent,"Share To:"))
            }

            rate.setOnClickListener(){

                val url = "https://play.google.com/store/apps/details?id="+packageName
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))

            }




        }


    override fun onemClick(position: Int) {


        val guidedata = guideList[position]
        val intent = Intent(applicationContext, Content::class.java)
        intent.putExtra(guide_data, guidedata)
        startActivity(intent)
        interstitialAd.loadAd()



        if  ( interstitialAd.isReady )
        {
            interstitialAd.showAd()

        }

        Log.d("bannerp","${interstitialAd.isReady }")

    }





        fun json(activity: MainActivity) {



            val queue = Volley.newRequestQueue(activity)
            val request = JsonArrayRequest(Request.Method.GET, url, null, { response ->

                Log.d("badrox","${response.length()}")
                try {
                        for (i in 0 until response.length()) {

                            val respObj = response.getJSONObject(i)
                            description = respObj.getString("description")
                            img = respObj.getString("img")
                            guideList.add(Model(description, img, R.drawable.ic_launcher_background))

                        }

                        mainReyclerView.setList(guideList)

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                }, { error ->
                    Toast.makeText(activity, "Fail to get response", Toast.LENGTH_SHORT)
                        .show()
                })
            queue.add(request)


        }


}

