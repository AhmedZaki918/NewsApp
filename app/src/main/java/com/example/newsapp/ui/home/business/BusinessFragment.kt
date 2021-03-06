package com.example.newsapp.ui.home.business

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.R
import com.example.newsapp.adapter.HomeAdapter
import com.example.newsapp.data.database.ArticleDao
import com.example.newsapp.data.model.Article
import com.example.newsapp.databinding.FragmentBusinessBinding
import com.example.newsapp.ui.details.DetailsActivity
import com.example.newsapp.util.OnItemClickListener
import com.example.newsapp.util.hide
import com.example.newsapp.util.startActivity
import com.example.newsapp.util.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


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

    @Inject
    lateinit var articleDao: ArticleDao


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentBusinessBinding.inflate(inflater, container, false)
        initViews()

        lifecycleScope.launch {
            viewModel.articles.collectLatest { pagedData ->
                homeAdapter.submitData(pagedData)
            }
        }

        binding.loadingIndicator.hide()
        return binding.root
    }


    private fun initViews() {
        homeAdapter = HomeAdapter(this, articleDao)
        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        binding.recyclerView.adapter = homeAdapter
        viewModel = ViewModelProvider(this).get(BusinessViewModel::class.java)
    }


    override fun onItemClick(article: Article?) {
        startActivity(
            requireActivity(), article!!, DetailsActivity::class.java
        )
    }


    override fun onSave(article: Article?) {
        viewModel.saveArticle(article)
        requireActivity().toast(R.string.saved)
    }


    override fun onDelete(article: Article?) {
        viewModel.deleteRequest(article)
        requireActivity().toast(R.string.removed)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}