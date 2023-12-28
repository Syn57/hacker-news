package com.ddb.hackernews.core.ui

import android.content.ClipData.Item
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ddb.hackernews.core.databinding.ItemTopStoriesBinding
import com.ddb.hackernews.core.domain.model.News
import com.ddb.hackernews.core.utils.DateFormatter

class TopNewsListAdapter(
    val moveDetail: (News) -> Unit
): ListAdapter<News, RecyclerView.ViewHolder>(NewsDiffCallBack()) {
    private class NewsDiffCallBack : DiffUtil.ItemCallback<News>(){
        override fun areItemsTheSame(oldItem: News, newItem: News): Boolean {
            return oldItem.idStory == newItem.idStory
        }

        override fun areContentsTheSame(oldItem: News, newItem: News): Boolean {
            return oldItem == newItem
        }
    }

    inner class ListViewHolder(private val binding: ItemTopStoriesBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: News) {
            with(binding) {
                tvItemAuthor.text = data.by
                tvItemScore.text = data.score.toString()
                tvItemComment.text = if (data.kids == null) "0" else data.kids.size.toString()
                tvItemTitle.text = data.title
                tvItemDate.text =
                    DateFormatter.format(data.time?.toLong()?.times(1000) ?: 1532358895000)
                itemView.setOnClickListener {
                    moveDetail(data)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemTopStoriesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = holder as ListViewHolder
        viewHolder.bind(getItem(position))
    }
}