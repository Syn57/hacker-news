package com.ddb.hackernews.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
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
import com.ddb.hackernews.core.ui.TopNewsListAdapter
import com.ddb.hackernews.databinding.FragmentHomeBinding
import com.ddb.hackernews.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by viewModel()
    private var adapter: TopNewsListAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = TopNewsListAdapter { selectedData ->
            val theStory = HomeFragmentDirections.actionHomeFragmentToDetailStoryFragment()
            theStory.storyClicked = selectedData
            Navigation.findNavController(view).navigate(theStory)
        }
        initAction()
        getData(view)
    }

    private fun getData(view: View) {
        if (activity != null) {
            homeViewModel.news.observe(viewLifecycleOwner) { news ->
                if (news != null) {
                    when (news) {
                        is Resource.Loading -> {
                            binding.shimmerTopStories.visibility = View.VISIBLE
                            binding.shimmerTopStories.startShimmer()
                            binding.rvTopStories.visibility = View.GONE
                        }

                        is Resource.Success -> {
                            binding.shimmerTopStories.visibility = View.GONE
                            binding.rvTopStories.visibility = View.VISIBLE
                            binding.shimmerTopStories.stopShimmer()
                            adapter?.submitList(news.data?.sortedBy { it.time })
                        }

                        is Resource.Error -> {
                            binding.shimmerTopStories.visibility = View.GONE
                            binding.shimmerTopStories.stopShimmer()
                            Toast.makeText(
                                activity,
                                getString(R.string.something_wrong),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }

            with(binding.rvTopStories) {
                layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
            }
            binding.rvTopStories.adapter = adapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        adapter = null
    }

    private fun initAction() {
        binding.lifecycleOwner = this
        //Retrieving latest favorite story
        homeViewModel.latestNewsFav.observe(viewLifecycleOwner) {
            binding.tvTitleFav.text = it?.title ?: "-"
            binding.tvAuthorFav.text = it?.by ?: "-"
        }
        //Move to detail news
        binding.cvListFav.setOnClickListener {
            try {
                moveToFavActivity()
            } catch (e: Exception) {
                Toast.makeText(requireActivity(), "Module not found", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun moveToFavActivity() {
        val uri = Uri.parse("hackernews://favorite")
        startActivity(
            Intent(
                Intent.ACTION_VIEW, uri
            )
        )
    }

}