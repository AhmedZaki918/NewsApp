package com.example.newsapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.data.database.ArticleDao
import com.example.newsapp.data.model.Article
import com.example.newsapp.databinding.LayoutHomeBinding
import com.example.newsapp.databinding.LayoutSecondBinding
import com.example.newsapp.util.Constants
import com.example.newsapp.util.OnItemClickListener
import com.squareup.picasso.Picasso

class HomeAdapter(
    val onItemClickListener: OnItemClickListener,
    val articleDao: ArticleDao
) : PagedListAdapter<Article, RecyclerView.ViewHolder>(Constants.USER_COMPARATOR) {


    // Create view holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        // First layout
        val layoutHomeBinding = LayoutHomeBinding.inflate(
            LayoutInflater.from(parent.context), parent,
            false
        )
        // Second layout
        val layoutSecondBinding = LayoutSecondBinding.inflate(
            LayoutInflater.from(parent.context), parent,
            false
        )
        if (viewType == 0) {
            return ViewHolderTwo(layoutSecondBinding)
        }
        return ViewHolderOne(layoutHomeBinding)
    }


    // Get type of view
    override fun getItemViewType(position: Int): Int {
        val url = getItem(position)?.urlToImage
        if (url == "" || url == null) {
            return 0
        }
        return 1
    }


    // Bind view holder
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
                    onItemClickListener.onSave(getItem(adapterPosition))
                } else {
                    onItemClickListener.onDelete(getItem(adapterPosition))
                }
            } else {
                onItemClickListener.onItemClick(getItem(adapterPosition))
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
                    onItemClickListener.onSave(getItem(adapterPosition))
                } else {
                    onItemClickListener.onDelete(getItem(adapterPosition))
                }
            } else {
                onItemClickListener.onItemClick(getItem(adapterPosition))
            }
        }
    }
}