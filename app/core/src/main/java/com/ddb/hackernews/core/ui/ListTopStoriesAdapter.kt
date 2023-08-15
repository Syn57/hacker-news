package com.ddb.hackernews.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ddb.hackernews.core.BuildConfig
import com.ddb.hackernews.core.data.source.remote.response.StoryResponse
import com.ddb.hackernews.core.databinding.ItemTopStoriesBinding
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ListTopStoriesAdapter(private val list: ArrayList<StoryResponse>?) :
    RecyclerView.Adapter<ListTopStoriesAdapter.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: StoryResponse)
    }

    class ListViewHolder(var binding: ItemTopStoriesBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding =
            ItemTopStoriesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (score, author, _, time, title, _, _, _, comments,_) = list!![position]
        holder.binding.tvItemAuthor.text = author
        holder.binding.tvItemScore.text = score.toString()
        holder.binding.tvItemComment.text = comments?.size?.toString() ?: "0"
        holder.binding.tvItemTitle.text = title
        val sdf = SimpleDateFormat(BuildConfig.DATE_FORMAT, Locale.TAIWAN)
        val date = Date(time?.toLong()?.times(1000) ?: 1532358895000)
        holder.binding.tvItemDate.text = sdf.format(date)
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(list[holder.adapterPosition]) }
    }

    override fun getItemCount(): Int = list!!.size
}