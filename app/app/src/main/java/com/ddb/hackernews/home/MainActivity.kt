package com.ddb.hackernews.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ddb.hackernews.R

class MainActivity : AppCompatActivity() {

//    private lateinit var binding: ActivityMainBinding
//    private lateinit var mainViewModel: MainViewModel
//    private lateinit var myAdapter: ListTopStoriesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
//        initAction()
////        setAdapter()
//        topStoriesRv()
//        //Refresh layout
//        binding.refreshLayout.setOnRefreshListener {
//            binding.shimmerTopStories.startShimmer()
//            topStoriesRv()
//            mainViewModel.isLoading.observe(this) {
//                if (!it) {
//                    binding.refreshLayout.isRefreshing = false
//                }
//            }
//        }
    }

//    private fun favorite(it: Story?) {
//        if (it == null) {
//            binding.tvTitleFav.text = "-"
//            binding.tvAuthorFav.text = "-"
//        } else {
//            binding.tvTitleFav.text = getString(R.string.title_fav_placeholder, it.title)
//            binding.tvAuthorFav.text = getString(R.string.author_fav_placeholder, it.by)
//        }
//    }

//    override fun onResume() {
//        super.onResume()
//        GlobalScope.launch {
//            mainViewModel.favorite()
//        }
//    }

//    private fun topStoriesRv() {
//        mainViewModel.getTopStories()
//        mainViewModel.stories.observe(this) {
//            val layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
//            binding.rvTopStories.layoutManager = layoutManager
//            val adapter = ListTopStoriesAdapter(it)
//            binding.rvTopStories.adapter = adapter
//            mainViewModel._isLoading.value = false
////            myAdapter = ListTopStoriesAdapter(it)
////            myAdapter.notifyDataSetChanged()
////            myAdapter.addAll
//            adapter.setOnItemClickCallback(object : ListTopStoriesAdapter.OnItemClickCallback {
//                override fun onItemClicked(data: StoryResponse) {
//                    val intent = Intent(this@MainActivity, DetailStoryActivity::class.java)
//                    intent.putExtra("data", data)
//                    startActivity(intent)
//                }
//            })
//        }
//    }

//    private fun setAdapter(){
//        val layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
//        binding.rvTopStories.layoutManager = layoutManager
//        binding.rvTopStories.adapter = myAdapter
//        myAdapter.setOnItemClickCallback(object : ListTopStoriesAdapter.OnItemClickCallback {
//            override fun onItemClicked(data: StoryResponse) {
//                val intent = Intent(this@MainActivity, DetailStoryActivity::class.java)
//                intent.putExtra("data", data)
//                startActivity(intent)
//            }
//        })
//
//    }

//    private fun initAction() {
//        //set view
//        supportActionBar?.hide()
//        //Initialize late init variables
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//        mainViewModel = obtainViewModel(this@MainActivity)
//        //Shimmer start
//        binding.shimmerTopStories.startShimmer()
//        mainViewModel.isLoading.observe(this) {
//            showShimmer(it)
//        }
//        //Retrieving latest favorite story
//        mainViewModel.story.observe(this) {
//            favorite(it)
//        }

//    }

//    private fun showShimmer(state: Boolean) {
//        binding.shimmerTopStories.visibility = if (state) View.VISIBLE else View.GONE
//        binding.rvTopStories.visibility = if (state) View.GONE else View.VISIBLE
//    }

//    private fun obtainViewModel(activity: AppCompatActivity): MainViewModel {
//        val factory = ViewModelFactory.getInstance(activity.application)
//        return ViewModelProvider(activity, factory)[MainViewModel::class.java]
//    }

    companion object {
        private const val TAG = "MainActivity"
    }

}