package com.ddb.hackernews.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ddb.hackernews.R
import com.ddb.hackernews.core.domain.model.News
import com.ddb.hackernews.databinding.ActivityDetailNewsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class DetailNewsActivity : AppCompatActivity() {

    private val detailNewsViewModel: DetailNewsViewModel by viewModel()
    private lateinit var binding: ActivityDetailNewsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val detailNews = intent.getParcelableExtra<News>(EXTRA_DATA)
        showDetailNews(detailNews)
    }

    private fun showDetailNews(detailNews: News?) {
        detailNews?.let{
            // TODO
        }
    }

    companion object {
        const val EXTRA_DATA = "extra_data"
    }
}