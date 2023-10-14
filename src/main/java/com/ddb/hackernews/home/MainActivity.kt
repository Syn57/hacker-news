package com.ddb.hackernews.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ddb.hackernews.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
    }

}