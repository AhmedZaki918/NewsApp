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
import com.example.newsapp.util.Constants
import com.example.newsapp.util.OnItemClickListener
import com.squareup.picasso.Picasso

class HomeAdapter(
    val onItemClickListener: OnItemClickListener,
    val articleDao: ArticleDao
) : PagingDataAdapter<Article, HomeAdapter.HomeViewHolder>(Constants.USER_COMPARATOR) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder(
            LayoutHomeBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }


    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }


    inner class HomeViewHolder(private val binding: LayoutHomeBinding) :
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
                    Picasso.get().load(article.urlToImage)
                        .resize(400, 400).placeholder(R.drawable.square)
                        .into(ivImage)
                }
            }
        }

        override fun onClick(v: View) {
            if (v.id == R.id.ib_save) {
                if (binding.ibSave.isChecked) {
                    onItemClickListener.onSave(getItem(layoutPosition))
                } else {
                    onItemClickListener.onDelete(getItem(layoutPosition))
                }
            } else {
                onItemClickListener.onItemClick(getItem(layoutPosition))
            }
        }
    }
}