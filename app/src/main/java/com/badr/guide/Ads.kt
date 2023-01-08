package com.badr.guide

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Handler
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import com.applovin.mediation.*
import com.applovin.mediation.ads.MaxAdView
import com.applovin.mediation.ads.MaxInterstitialAd
import com.applovin.mediation.nativeAds.MaxNativeAdListener
import com.applovin.mediation.nativeAds.MaxNativeAdLoader
import com.applovin.mediation.nativeAds.MaxNativeAdView
import com.applovin.sdk.AppLovinSdk
import com.applovin.sdk.AppLovinSdkConfiguration
import com.applovin.sdk.AppLovinSdkUtils
import java.util.concurrent.TimeUnit


class Ads : MaxAdListener , MaxAdViewAdListener  {

    companion object {

        lateinit var interstitialAd: MaxInterstitialAd
        private var retryAttempt = 0.0
        var adView: MaxAdView? = null
        private lateinit var nativeAdLoader: MaxNativeAdLoader
        private var nativeAd: MaxAd? = null
        var max_banner : String ="a2aecf350896f67d"
        var max_inter : String ="108990fc113bb498"
        var max_native : String ="64a5742e9ff620b2"
        var update :Boolean=false


    }
    fun adsinit(context: Context) {
        // Make sure to set the mediation provider value to "max" to ensure proper functionality
        AppLovinSdk.getInstance( context ).setMediationProvider( "max" )
        AppLovinSdk.getInstance( context ).initializeSdk({ configuration: AppLovinSdkConfiguration ->
        // AppLovin SDK is initialized, start loading ads
        })

        interstitialAd = MaxInterstitialAd(max_inter, context as Activity?)
        interstitialAd.setListener( this )

        // Load the first ad
        interstitialAd.loadAd()

    }



    @SuppressLint("ResourceAsColor")
    fun createBannerAd(activity: Activity) {
        adView = MaxAdView(max_banner, activity)
        adView?.setListener(this)

        // Stretch to the width of the screen for banners to be fully functional
        val width = ViewGroup.LayoutParams.MATCH_PARENT

        // Get the adaptive banner height.
        val heightDp = MaxAdFormat.BANNER.getAdaptiveSize(activity).height
        val heightPx = AppLovinSdkUtils.dpToPx(activity, heightDp)

        adView?.layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,heightPx, Gravity.BOTTOM)

        adView?.setExtraParameter("adaptive_banner", "true")

        // Set background or background color for banners to be fully functional
        adView?.setBackgroundColor(R.color.white)


        val rootView = activity.findViewById<ViewGroup>(android.R.id.content)
        rootView.addView(adView)

        // Load the ad
        adView?.loadAd()
    }


    fun createNativeAd(activity: Activity)
    {
        val nativeAdContainer = activity.findViewById<FrameLayout>(R.id.applovinnaive)

        nativeAdLoader = MaxNativeAdLoader( max_native, activity )
        nativeAdLoader.setNativeAdListener(object : MaxNativeAdListener() {

            override fun onNativeAdLoaded(nativeAdView: MaxNativeAdView?, ad: MaxAd)
            {
                // Clean up any pre-existing native ad to prevent memory leaks.
                if ( nativeAd != null )
                {
                    nativeAdLoader.destroy( nativeAd )
                }

                // Save ad for cleanup.
                nativeAd = ad

                // Add ad view to view.
                nativeAdContainer.removeAllViews()
                nativeAdContainer.addView( nativeAdView )

            }

            override fun onNativeAdLoadFailed(adUnitId: String, error: MaxError)
            {
                // We recommend retrying with exponentially higher delays up to a maximum delay
            }

            override fun onNativeAdClicked(ad: MaxAd)
            {
                // Optional click callback
            }
        })
        nativeAdLoader.loadAd()
    }

    // MAX Ad Listener
    override fun onAdLoaded(maxAd: MaxAd)
    {
        // Interstitial ad is ready to be shown. interstitialAd.isReady() will now return 'true'

        // Reset retry attempt
        retryAttempt = 0.0
    }

    override fun onAdLoadFailed(adUnitId: String?, error: MaxError?)
    {
        // Interstitial ad failed to load
        // AppLovin recommends that you retry with exponentially higher delays up to a maximum delay (in this case 64 seconds)

        retryAttempt++
        val delayMillis = TimeUnit.SECONDS.toMillis( Math.pow( 2.0, Math.min( 6.0, retryAttempt ) ).toLong() )

        Handler().postDelayed( { interstitialAd.loadAd() }, delayMillis )
    }

    override fun onAdDisplayFailed(ad: MaxAd?, error: MaxError?)
    {
        // Interstitial ad failed to display. AppLovin recommends that you load the next ad.
        interstitialAd.loadAd()
    }

    override fun onAdExpanded(ad: MaxAd?) {

    }

    override fun onAdCollapsed(ad: MaxAd?) {
    }

    override fun onAdDisplayed(maxAd: MaxAd) {}

    override fun onAdClicked(maxAd: MaxAd) {


    }

    override fun onAdHidden(maxAd: MaxAd)
    {
        // Interstitial ad is hidden. Pre-load the next ad
        interstitialAd.loadAd()
    }

}




