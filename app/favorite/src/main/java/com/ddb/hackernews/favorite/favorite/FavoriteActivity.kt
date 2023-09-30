package com.ddb.hackernews.favorite.favorite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.ddb.hackernews.core.ui.FavoriteAdapter
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
        val favAdapter = FavoriteAdapter()
        favoriteViewModel.newsFav.observe(this) {
            if (it != null) {
                favAdapter.setData(it.reversed())
            }
        }
        with(binding.rvFav) {
            layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
            adapter = favAdapter
        }
        binding.ivBackFav.setOnClickListener {
            finish()
        }

    }
}