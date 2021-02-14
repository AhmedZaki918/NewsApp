package com.example.newsapp.ui.home.headlines

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.adapter.HomeAdapter
import com.example.newsapp.data.model.Article
import com.example.newsapp.data.repository.HeadlinesRepo
import com.example.newsapp.databinding.FragmentHeadlinesBinding
import com.example.newsapp.ui.details.DetailsActivity
import com.example.newsapp.util.Constants
import com.example.newsapp.util.OnItemClickListener
import com.example.newsapp.util.hide


/**
 * A simple [Fragment] subclass.
 */
class HeadlinesFragment : Fragment(), OnItemClickListener {


    // Initialization
    private var _binding: FragmentHeadlinesBinding? = null
    private val binding get() = _binding!!
    private val homeAdapter = HomeAdapter(this)
    private lateinit var viewModel: HeadlinesViewModel
    private lateinit var factory: HeadlinesViewModelFactory
    private lateinit var repo: HeadlinesRepo


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHeadlinesBinding.inflate(inflater, container, false)

        // Assignment
        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        repo = HeadlinesRepo(activity!!.application)
        factory = HeadlinesViewModelFactory(repo)
        viewModel = ViewModelProvider(this, factory).get(HeadlinesViewModel::class.java)

        // Observe the changes from live data
        viewModel.newsPagedList.observe(viewLifecycleOwner, {
            updateUi(it)
        })
        return binding.root
    }


    override fun onItemClick(article: Article?) {
        val intent = Intent(activity, DetailsActivity::class.java)
        intent.putExtra(Constants.MODEL, article)
        context!!.startActivity(intent)
    }


    override fun saveItem(article: Article?) {
        viewModel.sendRequest(article)
    }


    private fun updateUi(list: PagedList<Article>) {
        homeAdapter.submitList(list)
        binding.recyclerView.adapter = homeAdapter
        binding.loadingIndicator.hide()
    }
}