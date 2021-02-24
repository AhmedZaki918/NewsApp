package com.example.newsapp.ui.home.business

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.R
import com.example.newsapp.adapter.HomeAdapter
import com.example.newsapp.data.model.Article
import com.example.newsapp.databinding.FragmentBusinessBinding
import com.example.newsapp.ui.details.DetailsActivity
import com.example.newsapp.util.Constants
import com.example.newsapp.util.OnItemClickListener
import com.example.newsapp.util.hide
import com.example.newsapp.util.toast
import dagger.hilt.android.AndroidEntryPoint


/**
 * A simple [Fragment] subclass.
 */
@AndroidEntryPoint
class BusinessFragment : Fragment(), OnItemClickListener {


    // Initialization
    private var _binding: FragmentBusinessBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: BusinessViewModel
    private lateinit var homeAdapter: HomeAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentBusinessBinding.inflate(inflater, container, false)

        // Assignment
        viewModel = ViewModelProvider(this).get(BusinessViewModel::class.java)
        // Observe the changes from live data
        viewModel.getData().observe(viewLifecycleOwner, {
            updateUi(it)
        })
        return binding.root
    }


    override fun onItemClick(article: Article?) {
        val intent = Intent(activity, DetailsActivity::class.java)
        intent.putExtra(Constants.MODEL, article)
        intent.putExtra("source", "Business")
        requireContext().startActivity(intent)
    }


    override fun saveItem(article: Article?) {
        viewModel.saveArticle(article)
        requireActivity().toast(resources.getString(R.string.saved))
    }


    override fun deleteItem(article: Article?) {
        TODO("Not yet implemented")
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    // Update the list of articles
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