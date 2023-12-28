package com.ddb.hackernews.core.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ddb.hackernews.core.databinding.ItemFavBinding
import com.ddb.hackernews.core.domain.model.News
import com.ddb.hackernews.core.utils.DateFormatter

class FavoriteListAdapter(
    val moveToDetail: (News) ->  Unit
): ListAdapter<News, RecyclerView.ViewHolder>(NewsDiffCallBack()) {

    private class NewsDiffCallBack: DiffUtil.ItemCallback<News>(){
        override fun areItemsTheSame(oldItem: News, newItem: News): Boolean {
            return oldItem.idStory == newItem.idStory
        }

        override fun areContentsTheSame(oldItem: News, newItem: News): Boolean {
            return oldItem == newItem
        }

    }

    inner class ListViewHolder(private val binding: ItemFavBinding) : RecyclerView.ViewHolder(binding.root) {
//        private val binding = ItemFavBinding.bind(itemView)
        fun bind(data: News) {
            with(binding) {
                tvInitialFav.text = data.by?.get(0)?.uppercase() ?: ""
                tvAuthorFav.text = data.by
                tvNumUpFav.text = data.score.toString()
                tvNumCommentFav.text = if (data.kids == null) "0" else data.kids.size.toString()
                tvTitleFav.text = data.title
                tvDateFav.text =
                    DateFormatter.format(data.time?.toLong()?.times(1000) ?: 1532358895000)
                itemView.setOnClickListener {
                    moveToDetail(data)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemFavBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = holder as ListViewHolder
        viewHolder.bind(getItem(position))
    }
}