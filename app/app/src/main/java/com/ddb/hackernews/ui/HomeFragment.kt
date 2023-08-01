package com.ddb.hackernews.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ddb.hackernews.R
import com.ddb.hackernews.adapter.ListTopStoriesAdapter
import com.ddb.hackernews.data.Story
import com.ddb.hackernews.data.StoryResponse
import com.ddb.hackernews.databinding.FragmentHomeBinding
import com.ddb.hackernews.viewmodel.MainViewModel
import com.ddb.hackernews.viewmodel.ViewModelFactory
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
//        return binding.root
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAction()
        topStoriesRv(view)
//        binding.refreshLayout.setOnRefreshListener {
////            binding.refreshLayout.isRefreshing
//            binding.shimmerTopStories.startShimmer()
//            topStoriesRv(view)
//            binding.refreshLayout.isRefreshing = true
//            mainViewModel.isLoading.observe(viewLifecycleOwner) {
//                binding.refreshLayout.isRefreshing = false
//                Log.d(TAG, "onViewCreatedIsload: $it")
//                if (!it) {
//                    Log.d(TAG, "onViewCreatedIsload2: ${!it}")
//                    binding.refreshLayout.isRefreshing = false
//                }
//            }
//        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        GlobalScope.launch {
            mainViewModel.favorite()
        }
    }

    fun initAction() {
        //Initialize late init variables
        mainViewModel = obtainViewModel(requireActivity() as AppCompatActivity)
        //Shimmer start
        binding.shimmerTopStories.startShimmer()
//        mainViewModel.isLoading.observe(viewLifecycleOwner) {
//            showShimmer(it)
//        }
        binding.mainViewModel = mainViewModel
        binding.lifecycleOwner = this
        //Retrieving latest favorite story
        mainViewModel.story.observe(viewLifecycleOwner) {
//            favorite(it)
        }

    }

    private fun topStoriesRv(view: View) {
        mainViewModel.getTopStories()
        mainViewModel.stories.observe(viewLifecycleOwner) {
            val layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
            binding.rvTopStories.layoutManager = layoutManager
            val adapter = ListTopStoriesAdapter(it)
            binding.rvTopStories.adapter = adapter
            mainViewModel._isLoading.value = false
//            myAdapter = ListTopStoriesAdapter(it)
//            myAdapter.notifyDataSetChanged()
//            myAdapter.addAll
            adapter.setOnItemClickCallback(object : ListTopStoriesAdapter.OnItemClickCallback {
                override fun onItemClicked(data: StoryResponse) {
                    Log.d(TAG, "onItemClicked: $data")
//                    val theStory = HomeFragmentDirections.actionHomeFragmentToDetailStoryActivity()
                    val theStory = HomeFragmentDirections.actionHomeFragmentToDetailStoryFragment(data)
//                    theStory.storyClicked = data
                    view.findNavController().navigate(theStory)
//                    view.findNavController().na
//                    val intent = Intent(this@MainActivity, DetailStoryActivity::class.java)
//                    intent.putExtra("data", data)
//                    startActivity(intent)

                }
            })
        }
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

//    private fun showShimmer(state: Boolean) {
//        binding.shimmerTopStories.visibility = if (state) View.VISIBLE else View.GONE
//        binding.rvTopStories.visibility = if (state) View.GONE else View.VISIBLE
//    }

    private fun obtainViewModel(activity: AppCompatActivity): MainViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[MainViewModel::class.java]
    }

    companion object {
        private const val TAG = "HomeFragment"
    }
}