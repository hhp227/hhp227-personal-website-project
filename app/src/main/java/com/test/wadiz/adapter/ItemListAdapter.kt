package com.test.wadiz.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.test.wadiz.data.ListData
import com.test.wadiz.databinding.ItemListBinding
import com.test.wadiz.request.ImageDownloadManager
import com.test.wadiz.util.PriceUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ItemListAdapter : ListAdapter<ListData, ItemListAdapter.ItemViewHolder>(ItemDiffCallback()) {
    lateinit var onItemClickListener: (View, Int) -> Unit

    lateinit var onKeywordClickListener: (View, Int) -> Unit

    lateinit var onSearchClickListener: (View, Int) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ItemViewHolder(private val binding: ItemListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ListData) {
            val keywordList = data.additionalInfo.split(", ")

            with(binding) {
                tvTitle.text = data.title
                tvType.text = data.type
                tvPrice.text = PriceUtil.getPrice(data.price)
                tvKeyword01.text = keywordList.find { it.contains("@") }
                tvKeyword02.text = keywordList.filter { it.contains("#") }[0]
                tvKeyword03.text = keywordList.filter { it.contains("#") }[1]

                CoroutineScope(Dispatchers.Main).launch {
                    ivImageThumbnail.setImageBitmap(ImageDownloadManager.getImage(data.photoUrl))
                }
            }
        }

        init {
            with(binding) {
                binding.cvItem.setOnClickListener { onItemClickListener.invoke(it, adapterPosition) }
                binding.tvKeyword01.setOnClickListener { onSearchClickListener.invoke(it, adapterPosition) }
                binding.tvKeyword02.setOnClickListener { onKeywordClickListener.invoke(it, adapterPosition) }
                binding.tvKeyword03.setOnClickListener { onKeywordClickListener.invoke(it, adapterPosition) }
            }
        }
    }
}

private class ItemDiffCallback : DiffUtil.ItemCallback<ListData>() {
    override fun areItemsTheSame(oldItem: ListData, newItem: ListData): Boolean {
        return oldItem.projectId == newItem.projectId
    }

    override fun areContentsTheSame(oldItem: ListData, newItem: ListData): Boolean {
        return oldItem == newItem
    }
}