package com.badr.guide

import android.view.View

class BadrAnimations {
    companion object{
        fun ridouxClickedAnimation(view: View, runnable: Runnable? = null){
            badrFadeInAnimation(view, 200)
            badrBouncingAnimation(view, 0.95f, 200){
                runnable?.run()
            }
        }

        fun badrBouncingAnimation(view: View, minScale: Float, time: Long, runnable: Runnable? = null){
            view.animate().apply {
                duration = time/2
                scaleX(minScale)
                scaleY(minScale)
            }.withEndAction {
                view.animate().apply {
                    duration = time/2
                    scaleX(1f)
                    scaleY(1f)
                }.withEndAction {
                    runnable?.run()
                }
            }
        }

        fun badrFadeInAnimation(view: View, time: Long, runnable: Runnable? = null){
            view.alpha = 0.1f
            view.visibility = View.VISIBLE
            view.animate().apply {
                duration = time
                alpha(1f)
            }.withEndAction {
                runnable?.run()
            }
        }

        fun badrFadeOutAnimation(view: View, time: Long, runnable: Runnable? = null){
            view.alpha = 1f
            view.visibility = View.VISIBLE
            view.animate().apply {
                duration = time
                alpha(0f)
            }.withEndAction {
                view.visibility = View.GONE
                runnable?.run()
            }
        }
    }
}