package com.example.newsapp.ui.home.business

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
import com.example.newsapp.data.repository.BusinessRepo
import com.example.newsapp.databinding.FragmentBusinessBinding
import com.example.newsapp.ui.details.DetailsActivity
import com.example.newsapp.util.Constants
import com.example.newsapp.util.OnItemClickListener
import com.example.newsapp.util.hide


/**
 * A simple [Fragment] subclass.
 */
class BusinessFragment : Fragment(), OnItemClickListener {


    // Initialization
    private var _binding: FragmentBusinessBinding? = null
    private val binding get() = _binding!!
    private val homeAdapter = HomeAdapter(this)
    private lateinit var viewModel: BusinessViewModel
    private lateinit var factory: BusinessViewModelFactory
    private lateinit var repo: BusinessRepo


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentBusinessBinding.inflate(inflater, container, false)

        // Assignment
        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        repo = BusinessRepo(activity!!.application)
        factory = BusinessViewModelFactory(repo)
        viewModel = ViewModelProvider(this, factory).get(BusinessViewModel::class.java)

        // Observe the changes from live data
        viewModel.newsPagedList.observe(viewLifecycleOwner, {
            updateUi(it)
        })
        return binding.root
    }


    override fun onItemClick(article: Article?) {
        val intent = Intent(activity, DetailsActivity::class.java)
        intent.putExtra(Constants.MODEL, article)
        intent.putExtra("source", "Business")
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