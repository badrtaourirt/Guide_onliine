package com.badr.guide

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.CheckBox
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import java.lang.Boolean

class Privacy : AppCompatActivity() {
    lateinit var policyButton: FrameLayout
    lateinit var policyCheckBox: CheckBox
    var prevStarted = "prevStarted"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_privacy)
        policyButton = findViewById(R.id.policyButton)
        policyCheckBox = findViewById(R.id.policyCheckBox)
        Log.d("badr","${policyCheckBox.isChecked}")


        val sharedpreferences = getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE)
        if (!sharedpreferences.getBoolean(prevStarted, false)) {
            val editor = sharedpreferences.edit()
            editor.putBoolean(prevStarted, Boolean.TRUE)
            editor.apply()
        } else {
            val intent = Intent(this@Privacy, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        Log.d("badro","${policyCheckBox.isChecked}")
        policyButton.setOnClickListener {
            policyCheckBox.setOnClickListener {  }
            BadrAnimations.badrBouncingAnimation(policyButton, 0.95f, 200){

                val intent = Intent(this@Privacy, MainActivity::class.java)
                startActivity(intent)
                !sharedpreferences.getBoolean(prevStarted, true)

            }
        }
    }



}