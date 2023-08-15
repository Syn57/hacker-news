package com.ddb.hackernews.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ddb.hackernews.core.BuildConfig
import com.ddb.hackernews.core.data.source.remote.response.CommentsResponse
import com.ddb.hackernews.core.databinding.ItemCommentsBinding
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ListCommentsAdapter(private val list: ArrayList<CommentsResponse>) :
    RecyclerView.Adapter<ListCommentsAdapter.ListViewHolder>() {

    class ListViewHolder(var binding: ItemCommentsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding =
            ItemCommentsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (_, author, _, content, time, _, _) = list[position]
        holder.binding.tvCommentsAuthor.text = author
        holder.binding.tvCommentsContent.text = content
        val sdf = SimpleDateFormat(BuildConfig.DATE_FORMAT, Locale.TAIWAN)
        val date = Date(time?.toLong()?.times(1000) ?: 1532358895000)
        holder.binding.tvCommentsDate.text = sdf.format(date)
    }

    override fun getItemCount(): Int = list.size
}