package com.example.newsapp.ui.details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.newsapp.R
import com.example.newsapp.data.model.Article
import com.example.newsapp.databinding.ActivityDetailsBinding
import com.example.newsapp.util.Constants
import com.squareup.picasso.Picasso


class DetailsActivity : AppCompatActivity(), View.OnClickListener {

    // Initialization
    private lateinit var binding: ActivityDetailsBinding
    private lateinit var article: Article
    private var source: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Assignment
        val data = intent
        article = data.getParcelableExtra(Constants.MODEL)!!
        source = data.getStringExtra("source")
        // Update ui for user
        updateUi()
        // Click listener
        binding.tvReadMore.setOnClickListener(this)
    }


    override fun onClick(v: View) {
        if (v.id == R.id.tv_read_more) {
            intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(article.url)
            startActivity(intent)
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }


    private fun updateUi() {
        // Display image
        val imageUrl = article.urlToImage
        Picasso.get()
            .load(imageUrl)
            .placeholder(R.drawable.square)
            .into(binding.imageView)
        // Update views
        binding.apply {
            tvTitle.text = article.title
            tvDescription.text = article.description
            tvContent.text = article.content
            tvSource.text = article.source!!.name
        }
        // Display the category of article
        if (source == resources.getText(R.string.business)) {
            binding.tvCategory.text = resources.getText(R.string.business)
        } else {
            binding.tvCategory.text = resources.getText(R.string.headlines)
        }
    }
}