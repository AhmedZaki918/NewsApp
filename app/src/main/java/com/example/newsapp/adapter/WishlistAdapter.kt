package com.example.newsapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.data.model.Article
import com.example.newsapp.databinding.LayoutWishilstBinding
import com.example.newsapp.ui.wishlist.WishlistListener
import com.squareup.picasso.Picasso

class WishlistAdapter(
    val wishlistListener: WishlistListener
) :
    RecyclerView.Adapter<WishlistAdapter.ViewHolder>() {

    // Initialization
    private lateinit var articleList: List<Article>

    // Create view holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WishlistAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val layoutWishlistBinding = LayoutWishilstBinding.inflate(
            layoutInflater, parent,
            false
        )
        return ViewHolder(layoutWishlistBinding)
    }

    // Bind view holder
    override fun onBindViewHolder(holder: WishlistAdapter.ViewHolder, position: Int) {
        // Get current position
        val currentItem = articleList[position]
        holder.binding.apply {
            tvTitle.text = currentItem.title
            tvDate.text = currentItem.publishedAt
            Picasso.get()
                .load(currentItem.urlToImage)
                .resize(400, 400)
                .placeholder(R.drawable.square)
                .into(ivArticle)
        }
    }

    // Return the size of list
    override fun getItemCount(): Int {
        return articleList.size
    }

    // View holder class
    inner class ViewHolder(val binding: LayoutWishilstBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.apply {
                itemView.setOnClickListener {
                    wishlistListener.onItemClick(articleList[adapterPosition])
                }

                ibDelete.setOnClickListener {
                    wishlistListener.onDelete(articleList[adapterPosition])
                }
            }
        }
    }

    // Setter for articles list
    fun submitList(list: List<Article>) {
        articleList = list
    }
}