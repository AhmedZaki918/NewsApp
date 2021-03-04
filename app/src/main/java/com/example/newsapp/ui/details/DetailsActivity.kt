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
import com.example.newsapp.util.checkNull
import com.squareup.picasso.Picasso


class DetailsActivity : AppCompatActivity(), View.OnClickListener {

    // Initialization
    private lateinit var binding: ActivityDetailsBinding
    private lateinit var article: Article


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Assignment
        val data = intent
        article = data.getParcelableExtra(Constants.MODEL)!!
        // Update ui for user
        updateUi()
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
        binding.apply {
            if (article.urlToImage == null) {
                imageView.visibility = View.GONE
            } else {
                Picasso.get()
                    .load(article.urlToImage)
                    .placeholder(R.drawable.square)
                    .into(imageView)
            }
        }
        // Check nullable from api and update ui
        binding.apply {
            checkNull(article.content, tvContent)
            checkNull(article.description, tvDescription)
            checkNull(article.author, tvAuthor)
            checkNull(article.title, tvTitle)
            checkNull(article.source?.name, tvSource)
        }
    }
}