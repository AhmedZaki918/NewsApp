package com.example.newsapp.ui.home.headlines

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.R
import com.example.newsapp.adapter.HomeAdapter
import com.example.newsapp.data.database.ArticleDao
import com.example.newsapp.data.model.Article
import com.example.newsapp.databinding.FragmentHeadlinesBinding
import com.example.newsapp.ui.details.DetailsActivity
import com.example.newsapp.util.OnItemClickListener
import com.example.newsapp.util.hide
import com.example.newsapp.util.startActivity
import com.example.newsapp.util.toast
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


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

    @Inject
    lateinit var articleDao: ArticleDao


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHeadlinesBinding.inflate(inflater, container, false)

        // Assignment
        homeAdapter = HomeAdapter(this, articleDao)
        viewModel = ViewModelProvider(this).get(HeadlinesViewModel::class.java)
//        // Observe the changes from live data
        viewModel.newsPagedList.observe(viewLifecycleOwner, {
            updateUi(it)
        })
        return binding.root
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


    private fun updateUi(list: PagedList<Article>) {
        binding.recyclerView.apply {
            homeAdapter.submitList(list)
            layoutManager = LinearLayoutManager(activity)
            adapter = homeAdapter
        }
        binding.loadingIndicator.hide()
    }
}