package com.badr.guide

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.applovin.mediation.ads.MaxAdView
import com.badr.guide.model.Model
import com.bumptech.glide.Glide

class Content : AppCompatActivity() {

    lateinit var imgcontent : ImageView
    private var adView: MaxAdView? = null
    lateinit var textcontent : TextView
    var ads: Ads = Ads()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content)

        imgcontent = findViewById(R.id.imgcontent)
        textcontent = findViewById(R.id.textcontent)
        ads.adsinit(this)
        ads.createNativeAd(this)
        ads.createBannerAd(this)

        val guidedata = intent.getSerializableExtra(MainActivity.guide_data) as Model
        Glide
            .with(this)
            .load(guidedata.img)
            .override(100, 200)
            .placeholder(R.drawable.ic_loading)
            .dontAnimate()
            .into(imgcontent);

        textcontent.text= guidedata.description


    }


}


