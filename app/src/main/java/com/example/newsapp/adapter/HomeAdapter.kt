package com.example.newsapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.data.database.ArticleDao
import com.example.newsapp.data.model.Article
import com.example.newsapp.databinding.LayoutHomeBinding
import com.example.newsapp.databinding.LayoutSecondBinding
import com.example.newsapp.util.Constants
import com.example.newsapp.util.OnAdapterClick
import com.squareup.picasso.Picasso

class HomeAdapter(
    val onAdapterClick: OnAdapterClick,
    val articleDao: ArticleDao
) : PagingDataAdapter<Article, RecyclerView.ViewHolder>(Constants.USER_COMPARATOR) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        // Second layout
        return if (viewType == 0) {
            ViewHolderTwo(
                LayoutSecondBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent, false
                )
            )
        }
        // First layout
        else ViewHolderOne(
            LayoutHomeBinding.inflate(
                LayoutInflater.from(parent.context), parent,
                false
            )
        )
    }


    // Get type of view
    override fun getItemViewType(position: Int): Int {
        val url = getItem(position)?.urlToImage
        if (url == "" || url == null) {
            return 0
        }
        return 1
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentItem = getItem(position)
        // Layout without image
        currentItem?.urlToImage.apply {
            if (this == "" || this == null) {
                val viewHolderTwo: ViewHolderTwo = holder as ViewHolderTwo
                viewHolderTwo.bind(currentItem)
            } else {
                // Layout with image
                val viewHolderOne: ViewHolderOne = holder as ViewHolderOne
                viewHolderOne.bind(currentItem)
            }
        }
    }


    // First view holder
    inner class ViewHolderOne(private val binding: LayoutHomeBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        init {
            itemView.setOnClickListener(this)
            binding.ibSave.setOnClickListener(this)
        }

        // Bind data
        fun bind(article: Article?) {
            binding.apply {
                // Update views
                if (article != null) {
                    tvTitle.text = article.title
                    tvSource.text = article.source?.name
                    // Update image
                    Picasso.get()
                        .load(article.urlToImage)
                        .resize(400, 400)
                        .placeholder(R.drawable.square)
                        .into(ivImage)
                }
            }
        }

        override fun onClick(v: View) {
            if (v.id == R.id.ib_save) {
                if (binding.ibSave.isChecked) {
                    onAdapterClick.onItemClick(getItem(layoutPosition), "save")
                } else {
                    onAdapterClick.onItemClick(getItem(layoutPosition), "remove")
                }
            } else {
                onAdapterClick.onItemClick(getItem(layoutPosition))
            }
        }
    }


    // Second view holder
    inner class ViewHolderTwo(private val binding: LayoutSecondBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        init {
            itemView.setOnClickListener(this)
            binding.ibSave.setOnClickListener(this)
        }

        // Bind data
        fun bind(article: Article?) {
            binding.apply {
                if (article != null) {
                    tvTitle.text = article.title
                    tvSource.text = article.source?.name
                }
            }
        }

        override fun onClick(v: View) {
            if (v.id == R.id.ib_save) {
                if (binding.ibSave.isChecked) {
                    onAdapterClick.onItemClick(getItem(layoutPosition), "save")
                } else {
                    onAdapterClick.onItemClick(getItem(layoutPosition), "remove")
                }
            } else {
                onAdapterClick.onItemClick(getItem(layoutPosition))
            }
        }
    }
}