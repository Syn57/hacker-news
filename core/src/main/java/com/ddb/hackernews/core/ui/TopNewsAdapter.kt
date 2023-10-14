package com.ddb.hackernews.core.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ddb.hackernews.core.R
import com.ddb.hackernews.core.databinding.ItemTopStoriesBinding
import com.ddb.hackernews.core.domain.model.News
import com.ddb.hackernews.core.utils.DateFormatter
import java.util.*

class TopNewsAdapter : RecyclerView.Adapter<TopNewsAdapter.ListViewHolder>() {

    private var listData = ArrayList<News>()
    var onItemClick: ((News) -> Unit)? = null

    fun setData(newListData: List<News>?) {
        if (newListData == null) return
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ListViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_top_stories, parent, false)
        )

    override fun getItemCount() = listData.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemTopStoriesBinding.bind(itemView)
        fun bind(data: News) {
            with(binding) {
                tvItemAuthor.text = data.by
                tvItemScore.text = data.score.toString()
                tvItemComment.text = if (data.kids == null) "0" else data.kids.size.toString()
                tvItemTitle.text = data.title
                tvItemDate.text =
                    DateFormatter.format(data.time?.toLong()?.times(1000) ?: 1532358895000)
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listData[adapterPosition])
            }
        }
    }
}