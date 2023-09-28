package com.ddb.hackernews.favorite.favorite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.ddb.hackernews.favorite.R
import com.ddb.hackernews.favorite.databinding.ActivityFavoriteBinding
import com.ddb.hackernews.favorite.di.favModule
import com.ddb.hackernews.favorite.viewmodel.FavoriteViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.GlobalContext.loadKoinModules

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private val favoriteViewModel: FavoriteViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        loadKoinModules(favModule)
        favoriteViewModel.newsFav.observe(this){
            Log.d("FavAct", "onCreate: $it")
        }

    }
}