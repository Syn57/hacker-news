package com.ddb.hackernews.core.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ddb.hackernews.core.R
import com.ddb.hackernews.core.databinding.ItemFavBinding
import com.ddb.hackernews.core.domain.model.News
import com.ddb.hackernews.core.utils.DateFormatter
import java.util.ArrayList

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.ListViewHolder>() {

    private var listData = ArrayList<News>()
    var onItemClick: ((News) -> Unit)? = null

    fun setData(newListData: List<News>?) {
        if (newListData == null) return
        listData.clear()
        listData.addAll(newListData)
//        notifyDataSetChanged()
        notifyItemRangeInserted(0,newListData.size)
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemFavBinding.bind(itemView)
        fun bind(data: News) {
            with(binding) {
                tvInitialFav.text = data.by?.get(0)?.uppercase() ?: ""
                tvAuthorFav.text = data.by
                tvNumUpFav.text = data.score.toString()
                tvNumCommentFav.text = if (data.kids == null) "0" else data.kids.size.toString()
                tvTitleFav.text = data.title
                tvDateFav.text =
                    DateFormatter.format(data.time?.toLong()?.times(1000) ?: 1532358895000)
            }
        }
        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listData[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder =
        ListViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_fav, parent, false)
        )

    override fun getItemCount() = listData.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
    }
}