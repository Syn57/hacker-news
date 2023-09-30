package com.ddb.hackernews.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.ddb.hackernews.R
import com.ddb.hackernews.core.data.Resource
import com.ddb.hackernews.core.domain.model.News
import com.ddb.hackernews.core.ui.CommentsAdapter
import com.ddb.hackernews.core.utils.DateFormatter
import com.ddb.hackernews.databinding.FragmentDetailNewsBinding
import com.ddb.hackernews.viewmodel.DetailNewsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class DetailNewsFragment : Fragment() {

    private var _binding: FragmentDetailNewsBinding? = null
    private val binding get() = _binding!!
    private val detailNewsViewModel: DetailNewsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_detail_news, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAction(view)
        val storyClicked = DetailNewsFragmentArgs.fromBundle(arguments as Bundle).storyClicked
        showDetail(storyClicked)
        val commentsAdapter = CommentsAdapter()
        favCondition(storyClicked)
        detailNewsViewModel.getAllComments(storyClicked?.kids).observe(viewLifecycleOwner) {
            if (storyClicked?.kids != null) {
                if (it.data != null) {
                    when (it) {
                        is Resource.Loading -> {
                            binding.shimmerComments.visibility = View.VISIBLE
                            binding.shimmerComments.startShimmer()
                        }

                        is Resource.Success -> {
                            binding.shimmerComments.visibility = View.GONE
                            binding.shimmerComments.stopShimmer()
                            commentsAdapter.setData(it.data)
                        }

                        is Resource.Error -> {
                            binding.shimmerComments.visibility = View.GONE
                            binding.shimmerComments.stopShimmer()
                            Toast.makeText(
                                activity,
                                getString(R.string.something_wrong),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            } else {
                binding.shimmerComments.visibility = View.GONE
            }
        }

        with(binding.rvComments) {
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = commentsAdapter
        }
    }

    private fun showDetail(news: News? = null) {
        binding.story = news
        binding.tvDetailDate.text =
            DateFormatter.format(news?.time?.toLong()?.times(1000) ?: 1532358895000)
    }

    private fun initAction(view: View) {
        binding.lifecycleOwner = this
        //Shimmer start
        binding.shimmerComments.startShimmer()

        //Back button
        binding.ivBack.setOnClickListener {
            Navigation.findNavController(view).popBackStack()
        }
    }


    private fun favCondition(story: News?) {
        var flag = false
        detailNewsViewModel.getIsFav(story?.idStory ?: 1).observe(viewLifecycleOwner) { isFav ->
            flag = isFav
            setBgFav(flag)
        }
        binding.btnDetailStar.setOnClickListener {
            flag = !flag
            if (flag) {
                setBgFav(flag)
                detailNewsViewModel.setFavoriteNews(story, true)
                Toast.makeText(activity, "Added to favorite", Toast.LENGTH_SHORT).show()
            } else {
                setBgFav(flag)
                detailNewsViewModel.setFavoriteNews(story, false)
                Toast.makeText(activity, "Removed from favorite", Toast.LENGTH_SHORT).show()
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

}