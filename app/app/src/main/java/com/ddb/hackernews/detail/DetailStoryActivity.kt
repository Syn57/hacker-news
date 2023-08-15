package com.ddb.hackernews.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ddb.hackernews.BuildConfig
import com.ddb.hackernews.R
import com.ddb.hackernews.core.ui.ListCommentsAdapter
import com.ddb.hackernews.core.data.source.remote.response.StoryResponse
import com.ddb.hackernews.databinding.ActivityDetailStoryBinding
import com.ddb.hackernews.viewmodel.ViewModelFactory
import java.text.SimpleDateFormat
import java.util.*

class DetailStoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailStoryBinding
    private lateinit var detailStoryViewModel: DetailStoryViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_story)
        initAction()

        //Get object Story Response from MainActivity
//        val storyClicked = intent.getParcelableExtra<StoryResponse>("data")
        val storyClicked = DetailStoryActivityArgs.fromBundle(intent.extras!!).storyClicked
        Log.d(TAG, "onCreate: $storyClicked")

//        showDetail(storyClicked)
//        showComments(storyClicked?.kids)
//        favCondition(storyClicked)

    }

    private fun initAction() {
        //set view
        supportActionBar?.hide()
        //Initialize late init variables
//        binding = ActivityDetailStoryBinding.inflate(layoutInflater)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_story)
        setContentView(binding.root)

        detailStoryViewModel = obtainViewModel(this@DetailStoryActivity)
        binding.detailViewModel = detailStoryViewModel
        binding.lifecycleOwner = this
        //Shimmer start
        binding.shimmerComments.startShimmer()

        //Back button
        binding.ivBack.setOnClickListener {
            finish()
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun showComments(ids: List<Int?>?) {
        if (ids != null) {
            detailStoryViewModel.getComments(ids as List<Int>)
        }
        detailStoryViewModel.comments.observe(this) {
            val layoutManager = LinearLayoutManager(this)
            binding.rvComments.layoutManager = layoutManager
            val adapter = ListCommentsAdapter(it)
            binding.rvComments.adapter = adapter
            detailStoryViewModel._isLoading.value = false
            binding.rvComments.visibility = View.VISIBLE
            binding.shimmerComments.visibility = View.GONE
        }
    }

    private fun showDetail(storyResponse: StoryResponse? = null) {
//        binding.tvDetailTitle.text = storyResponse?.title
//        binding.tvDetailDesc.text = storyResponse?.url
        binding.story = storyResponse
        val sdf = SimpleDateFormat(BuildConfig.DATE_FORMAT, Locale.TAIWAN)
        val date = Date(storyResponse?.time?.toLong()?.times(1000) ?: 1532358895000)
        binding.tvDetailDate.text = sdf.format(date)
//        binding.tvDetailAuthor.text = storyResponse?.by
    }

    private fun favCondition(story: StoryResponse?) {
        var flag = false
        detailStoryViewModel.getIsFav(story?.id ?: 1).observe(this) { isFav ->
            flag = isFav
            setBgFav(flag)
        }
        binding.btnDetailStar.setOnClickListener {
            flag = !flag
            Log.d(TAG, "favCondition2: $flag")
            if (flag) {
                setBgFav(flag)
                story?.isFav = true
                detailStoryViewModel.update(story)
                Toast.makeText(this, "Added to favorite", Toast.LENGTH_SHORT).show()
            } else {
                setBgFav(flag)
                story?.isFav = false
                detailStoryViewModel.update(story)
//                data.idStory?.let { it1 -> detailStoryViewModel.delete(it1) }
                Toast.makeText(this, "Removed from favorite", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun setBgFav(state: Boolean) {
        if (state) {
            binding.btnDetailStar.setImageResource(R.drawable.ic_star_orange)
        } else {
            binding.btnDetailStar.setImageResource(R.drawable.ic_star_grey)
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): DetailStoryViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[DetailStoryViewModel::class.java]
    }

    companion object {
        private const val TAG = "DetailStoryActivity"
        const val EXTRA_DATA = "extra_data"
    }
}