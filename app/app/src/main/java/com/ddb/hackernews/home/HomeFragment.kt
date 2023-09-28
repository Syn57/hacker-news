package com.ddb.hackernews.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ddb.hackernews.R
import com.ddb.hackernews.core.data.Resource
import com.ddb.hackernews.core.ui.TopNewsAdapter
import com.ddb.hackernews.databinding.FragmentHomeBinding
import com.ddb.hackernews.viewmodel.HomeViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAction()

//        topStoriesRv(view)
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

        if(activity!=null){
            val newsAdapter = TopNewsAdapter()
            newsAdapter.onItemClick = { selectedData ->
                val theStory = HomeFragmentDirections.actionHomeFragmentToDetailStoryFragment()
                theStory.storyClicked = selectedData
                Navigation.findNavController(view).navigate(theStory)
            }
            homeViewModel.news.observe(viewLifecycleOwner){ news ->
                if(news!=null){
                    Log.d(TAG, "onViewCreatedNew: ${news.data}")
                    when (news){
                        is Resource.Loading -> {
                            binding.shimmerTopStories.visibility = View.VISIBLE
                            binding.shimmerTopStories.startShimmer()
                        }
                        is Resource.Success -> {
                            binding.shimmerTopStories.visibility = View.GONE
                            binding.shimmerTopStories.stopShimmer()
                            newsAdapter.setData(news.data?.sortedByDescending { it.time })
                        }
                        is Resource.Error -> {
                            binding.shimmerTopStories.visibility = View.GONE
                            binding.shimmerTopStories.stopShimmer()
                            Toast.makeText(activity, getString(R.string.something_wrong), Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

            with(binding.rvTopStories){
                layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
                adapter = newsAdapter
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        GlobalScope.launch {
//            mainViewModel.favorite()
        }
    }

    fun initAction() {
        //Shimmer start
        binding.shimmerTopStories.startShimmer()
        binding.lifecycleOwner = this
        //Retrieving latest favorite story
        homeViewModel.latestNewsFav.observe(viewLifecycleOwner){
            binding.tvTitleFav.text = it?.title ?: "-"
            binding.tvAuthorFav.text = it?.by ?: "-"
        }
        //Move to detail news
        binding.cvListFav.setOnClickListener {
            try {
                moveToFavActivity()
            } catch (e: Exception){
                Toast.makeText(requireActivity(), "Module not found", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun moveToFavActivity() {
        startActivity(Intent(requireActivity(), Class.forName("com.ddb.hackernews.favorite.favorite.FavoriteActivity")))
    }

    companion object {
        private const val TAG = "HomeFragment"
    }
}