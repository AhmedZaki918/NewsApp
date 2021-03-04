package com.example.newsapp.adapter

import android.view.LayoutInflater
import android.view.View
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
    private var list: List<Article> = listOf()


    // Create view holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WishlistAdapter.ViewHolder {
        val layoutWishlistBinding = LayoutWishilstBinding.inflate(
            LayoutInflater.from(parent.context), parent,
            false
        )
        return ViewHolder(layoutWishlistBinding)
    }


    // Bind view holder
    override fun onBindViewHolder(holder: WishlistAdapter.ViewHolder, position: Int) {
        // Get current position
        val currentItem = list[position]
        holder.bind(currentItem)
    }


    // Return the size of list
    override fun getItemCount(): Int {
        return list.size
    }


    // View holder class
    inner class ViewHolder(private val binding: LayoutWishilstBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        init {
            binding.apply {
                ibDelete.setOnClickListener(this@ViewHolder)
                ibShare.setOnClickListener(this@ViewHolder)
                itemView.setOnClickListener(this@ViewHolder)
            }
        }

        // Bind data
        fun bind(article: Article) {
            binding.apply {
                tvTitle.text = article.title
                tvDate.text = article.publishedAt
                Picasso.get()
                    .load(article.urlToImage)
                    .resize(400, 400)
                    .placeholder(R.drawable.square)
                    .into(ivArticle)
            }
        }

        override fun onClick(v: View) {
            when (v.id) {
                R.id.ib_delete -> {
                    wishlistListener.onDelete(list[adapterPosition])
                }
                R.id.ib_share -> {
                    wishlistListener.onShare(list[adapterPosition])
                }
                else -> {
                    wishlistListener.onItemClick(list[adapterPosition])
                }
            }
        }
    }


    // Setter for articles list
    fun submitList(list: List<Article>) {
        this.list = list
    }
}