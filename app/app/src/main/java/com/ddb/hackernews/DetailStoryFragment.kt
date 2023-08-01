package com.ddb.hackernews

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ddb.hackernews.adapter.ListCommentsAdapter
import com.ddb.hackernews.data.StoryResponse
import com.ddb.hackernews.databinding.FragmentDetailStoryBinding
import com.ddb.hackernews.ui.DetailStoryActivityArgs
import com.ddb.hackernews.viewmodel.DetailStoryViewModel
import com.ddb.hackernews.viewmodel.ViewModelFactory
import java.text.SimpleDateFormat
import java.util.*


class DetailStoryFragment : Fragment() {

    private var _binding: FragmentDetailStoryBinding? = null
    private val binding get() = _binding!!
    private lateinit var detailStoryViewModel: DetailStoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail_story, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAction()
        val storyClicked = DetailStoryActivityArgs.fromBundle(arguments as Bundle).storyClicked
        showDetail(storyClicked)
        showComments(storyClicked?.kids)
        favCondition(storyClicked)

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

    private fun initAction() {
        //Initialize late init variables
//        binding = ActivityDetailStoryBinding.inflate(layoutInflater)

        detailStoryViewModel = obtainViewModel(requireActivity() as AppCompatActivity)
        binding.detailViewModel = detailStoryViewModel
        binding.lifecycleOwner = this
        //Shimmer start
        binding.shimmerComments.startShimmer()

        //Back button
        binding.ivBack.setOnClickListener {
            getFragmentManager()?.popBackStack()
        }
    }

    private fun showComments(ids: List<Int?>?) {
        if (ids != null) {
            detailStoryViewModel.getComments(ids as List<Int>)
        }
        detailStoryViewModel.comments.observe(viewLifecycleOwner) {
            val layoutManager = LinearLayoutManager(requireActivity())
            binding.rvComments.layoutManager = layoutManager
            val adapter = ListCommentsAdapter(it)
            binding.rvComments.adapter = adapter
            detailStoryViewModel._isLoading.value = false
            binding.rvComments.visibility = View.VISIBLE
            binding.shimmerComments.visibility = View.GONE
        }
    }

    private fun favCondition(story: StoryResponse?) {
        var flag = false
        detailStoryViewModel.getIsFav(story?.id ?: 1).observe(viewLifecycleOwner) { isFav ->
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
                Toast.makeText(activity, "Added to favorite", Toast.LENGTH_SHORT).show()
            } else {
                setBgFav(flag)
                story?.isFav = false
                detailStoryViewModel.update(story)
//                data.idStory?.let { it1 -> detailStoryViewModel.delete(it1) }
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

    private fun obtainViewModel(activity: AppCompatActivity): DetailStoryViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[DetailStoryViewModel::class.java]
    }

    companion object {
        private const val TAG = "DetailStoryFragment"
    }


}