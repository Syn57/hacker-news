package com.ddb.hackernews.favorite.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.ddb.hackernews.core.ui.FavoriteListAdapter
import com.ddb.hackernews.favorite.R
import com.ddb.hackernews.favorite.databinding.FragmentFavBinding
import com.ddb.hackernews.favorite.viewmodel.FavoriteViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavFragment : Fragment() {

    private var _binding: FragmentFavBinding? = null
    private val binding get() = _binding!!
    private val favoriteViewModel: FavoriteViewModel by viewModel()
    private var adapter: FavoriteListAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_fav, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = FavoriteListAdapter { selectedData ->
            val theStory = FavFragmentDirections.actionFavFragmentToDetailFragmentFav()
            theStory.storyClicked = selectedData
            Navigation.findNavController(view).navigate(theStory)
        }
        favoriteViewModel.newsFav.observe(viewLifecycleOwner) {
            if (it != null) {
                adapter?.submitList(it.reversed())
            }
        }
        with(binding.rvFav) {
            layoutManager = LinearLayoutManager(requireContext())
        }
        binding.rvFav.adapter = adapter
        binding.ivBackFav.setOnClickListener {
            requireActivity().finish()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        adapter = null
    }

}