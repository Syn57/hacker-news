package com.ddb.hackernews.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.ddb.hackernews.BuildConfig
import com.ddb.hackernews.R
import com.ddb.hackernews.core.data.Resource
import com.ddb.hackernews.core.domain.model.News
import com.ddb.hackernews.core.ui.CommentsAdapter
import com.ddb.hackernews.databinding.FragmentDetailNewsBinding
import com.ddb.hackernews.viewmodel.DetailNewsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*


class DetailNewsFragment : Fragment() {

    private var _binding: FragmentDetailNewsBinding? = null
    private val binding get() = _binding!!
    private val detailNewsViewModel: DetailNewsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail_news, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAction()
        val storyClicked = DetailNewsFragmentArgs.fromBundle(arguments as Bundle).storyClicked
        showDetail(storyClicked)
        val commentsAdapter = CommentsAdapter()
        Log.d(TAG, "onViewCreated: ${storyClicked?.kids}")
        detailNewsViewModel.getAllComments(storyClicked?.kids).observe(viewLifecycleOwner){
            Log.d(TAG, "comments")
            if (it.data!= null){
                Log.d(TAG, "comments: ${it.data}")
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
                        Toast.makeText(activity, getString(R.string.something_wrong), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        with(binding.rvComments){
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = commentsAdapter
        }
    }

    private fun showDetail(news: News? = null) {
        binding.story = news
        val sdf = SimpleDateFormat(BuildConfig.DATE_FORMAT, Locale.TAIWAN)
        val date = Date(news?.time?.toLong()?.times(1000) ?: 1532358895000)
        binding.tvDetailDate.text = sdf.format(date)

    }

    private fun initAction() {
        binding.lifecycleOwner = this
        //Shimmer start
        binding.shimmerComments.startShimmer()

        //Back button
        binding.ivBack.setOnClickListener {
            getFragmentManager()?.popBackStack()
        }
    }


//    private fun favCondition(story: News?) {
//        var flag = false
//        detailStoryViewModel.getIsFav(story?.id ?: 1).observe(viewLifecycleOwner) { isFav ->
//            flag = isFav
//            setBgFav(flag)
//        }
//        binding.btnDetailStar.setOnClickListener {
//            flag = !flag
//            Log.d(TAG, "favCondition2: $flag")
//            if (flag) {
//                setBgFav(flag)
//                story?.isFav = true
//                detailStoryViewModel.update(story)
//                Toast.makeText(activity, "Added to favorite", Toast.LENGTH_SHORT).show()
//            } else {
//                setBgFav(flag)
//                story?.isFav = false
//                detailStoryViewModel.update(story)
////                data.idStory?.let { it1 -> detailStoryViewModel.delete(it1) }
//                Toast.makeText(activity, "Removed from favorite", Toast.LENGTH_SHORT).show()
//            }
//        }
//
//    }

    private fun setBgFav(state: Boolean) {
        if (state) {
            binding.btnDetailStar.setImageResource(R.drawable.ic_star_orange)
        } else {
            binding.btnDetailStar.setImageResource(R.drawable.ic_star_grey)
        }
    }

    companion object {
        private const val TAG = "DetailStoryFragment"
        const val EXTRA_DATA = "extra_data"
    }


}