package com.ddb.hackernews.core.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ddb.hackernews.core.BuildConfig
import com.ddb.hackernews.core.R
import com.ddb.hackernews.core.databinding.ItemTopStoriesBinding
import com.ddb.hackernews.core.domain.model.News
import java.text.SimpleDateFormat
import java.util.*

class TopNewsAdapter: RecyclerView.Adapter<TopNewsAdapter.ListViewHolder>() {

    private var listData = ArrayList<News>()
    var onItemClick: ((News) -> Unit)? = null

    fun setData(newListData: List<News>?) {
        if (newListData == null) return
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_top_stories, parent, false))

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
                tvItemComment.text = data.kids?.size.toString()
                tvItemTitle.text = data.title
                val sdf = SimpleDateFormat(BuildConfig.DATE_FORMAT, Locale.TAIWAN)
                val date = Date(data.time?.toLong()?.times(1000) ?: 1532358895000)
                tvItemDate.text = sdf.format(date)
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listData[adapterPosition])
            }
        }
    }
}