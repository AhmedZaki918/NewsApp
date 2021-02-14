package com.example.newsapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.data.model.Article
import com.example.newsapp.databinding.LayoutHomeBinding
import com.example.newsapp.databinding.LayoutSecondBinding
import com.example.newsapp.util.Constants
import com.example.newsapp.util.OnItemClickListener
import com.squareup.picasso.Picasso


class HomeAdapter(val onItemClickListener: OnItemClickListener) :
    PagedListAdapter<Article, RecyclerView.ViewHolder>(Constants.USER_COMPARATOR) {


    override fun getItemViewType(position: Int): Int {
        val url = getItem(position)?.urlToImage
        if (url == "" || url == null) {
            return 0
        }
        return 1
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        // First layout
        val layoutHomeBinding = LayoutHomeBinding.inflate(
            layoutInflater, parent,
            false
        )
        // Second layout
        val layoutSecondBinding = LayoutSecondBinding.inflate(
            layoutInflater, parent,
            false
        )
        if (viewType == 0) {
            return ViewHolderTwo(layoutSecondBinding)
        }
        return ViewHolderOne(layoutHomeBinding)
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val url = getItem(position)?.urlToImage
        val currentItem = getItem(position)
        // Layout without image
        if (url == "" || url == null) {
            val viewHolderTwo: ViewHolderTwo = holder as ViewHolderTwo
            viewHolderTwo.binding.apply {
                // Update views
                tvTitle.text = currentItem?.title
                tvSource.text = currentItem?.source?.name
            }
        } else {
            // Layout with image
            val viewHolderOne: ViewHolderOne = holder as ViewHolderOne
            viewHolderOne.binding.apply {
                // Update views
                tvTitle.text = currentItem?.title
                tvSource.text = currentItem?.source?.name
                Picasso.get()
                    .load(currentItem?.urlToImage)
                    .resize(400,400)
                    .placeholder(R.drawable.square)
                    .into(ivImage)
            }
        }
    }


    inner class ViewHolderOne(val binding: LayoutHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                onItemClickListener.onItemClick(getItem(adapterPosition))
            }
            binding.ibSave.setOnClickListener {
                onItemClickListener.saveItem(getItem(adapterPosition))
            }
        }
    }


    inner class ViewHolderTwo(val binding: LayoutSecondBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                onItemClickListener.onItemClick(getItem(adapterPosition))
            }
            binding.ibSave.setOnClickListener {
                onItemClickListener.saveItem(getItem(adapterPosition))
            }
        }
    }
}