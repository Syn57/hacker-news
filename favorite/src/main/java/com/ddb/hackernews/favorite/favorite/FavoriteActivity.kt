package com.ddb.hackernews.favorite.favorite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ddb.hackernews.favorite.R
import com.ddb.hackernews.favorite.di.favModule
import org.koin.core.context.GlobalContext.loadKoinModules

class FavoriteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)
        supportActionBar?.hide()
        loadKoinModules(favModule)

    }
}