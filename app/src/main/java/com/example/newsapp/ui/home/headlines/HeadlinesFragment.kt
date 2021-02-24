package com.example.newsapp.ui.home.headlines

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.adapter.HomeAdapter
import com.example.newsapp.data.model.Article
import com.example.newsapp.databinding.FragmentHeadlinesBinding
import com.example.newsapp.ui.details.DetailsActivity
import com.example.newsapp.util.Constants
import com.example.newsapp.util.OnItemClickListener
import com.example.newsapp.util.hide
import dagger.hilt.android.AndroidEntryPoint


/**
 * A simple [Fragment] subclass.
 */
@AndroidEntryPoint
class HeadlinesFragment : Fragment(), OnItemClickListener {


    // Initialization
    private var _binding: FragmentHeadlinesBinding? = null
    private val binding get() = _binding!!
    private lateinit var homeAdapter: HomeAdapter
    private lateinit var viewModel: HeadlinesViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHeadlinesBinding.inflate(inflater, container, false)

        // Assignment
        viewModel = ViewModelProvider(this).get(HeadlinesViewModel::class.java)
        // Observe the changes from live data
        viewModel.getArticles().observe(viewLifecycleOwner, {
            updateUi(it)
        })
        return binding.root
    }


    override fun onItemClick(article: Article?) {
        val intent = Intent(activity, DetailsActivity::class.java)
        intent.putExtra(Constants.MODEL, article)
        requireContext().startActivity(intent)
    }


    override fun saveItem(article: Article?) {
        viewModel.saveArticle(article)
    }


    override fun deleteItem(article: Article?) {
        viewModel.deleteRequest(article)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun updateUi(list: List<Article>) {
        homeAdapter = HomeAdapter(this, list)
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
            adapter = homeAdapter
        }
        binding.loadingIndicator.hide()
    }
}