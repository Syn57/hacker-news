package com.ddb.hackernews.core.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ddb.hackernews.core.R
import com.ddb.hackernews.core.databinding.ItemCommentsBinding
import com.ddb.hackernews.core.domain.model.Comment
import com.ddb.hackernews.core.utils.DateFormatter
import java.util.*

class CommentsAdapter : RecyclerView.Adapter<CommentsAdapter.ListViewHolder>() {

    private var listData = ArrayList<Comment>()

    fun setData(newListData: List<Comment>?) {
        if (newListData == null) return
        listData.clear()
        listData.addAll(newListData)
//        notifyDataSetChanged()
        notifyItemRangeInserted(0,newListData.size)

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CommentsAdapter.ListViewHolder =
        ListViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_comments, parent, false)
        )


    override fun onBindViewHolder(holder: CommentsAdapter.ListViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
    }

    override fun getItemCount() = listData.size

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemCommentsBinding.bind(itemView)
        fun bind(data: Comment) {
            with(binding) {
                tvCommentsAuthor.text = data.by
                tvCommentsContent.text = data.text
                tvCommentsDate.text =
                    DateFormatter.format(data.time?.toLong()?.times(1000) ?: 1532358895000)
            }
        }
    }

}