package com.example.newsapp.ui.home.tech

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.R
import com.example.newsapp.adapter.HomeAdapter
import com.example.newsapp.data.database.ArticleDao
import com.example.newsapp.data.model.Article
import com.example.newsapp.databinding.FragmentTechBinding
import com.example.newsapp.ui.details.DetailsActivity
import com.example.newsapp.util.Coroutines
import com.example.newsapp.util.OnAdapterClick
import com.example.newsapp.util.hide
import com.example.newsapp.util.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject


/**
 * A simple [Fragment] subclass.
 */
@AndroidEntryPoint
class TechFragment : Fragment(), OnAdapterClick {


    // Initialization
    private var _binding: FragmentTechBinding? = null
    private val binding get() = _binding!!
    private lateinit var homeAdapter: HomeAdapter
    private lateinit var viewModel: TechViewModel


    @Inject
    lateinit var articleDao: ArticleDao


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentTechBinding.inflate(inflater, container, false)
        initViews()

        Coroutines.main {
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
        viewModel = ViewModelProvider(this).get(TechViewModel::class.java)
    }


    override fun onItemClick(article: Article?, operation: String) {
        when (operation) {
            "save" -> {
                viewModel.createOperation(article, operation)
                requireActivity().toast(R.string.saved)
            }
            "remove" -> {
                viewModel.createOperation(article, operation)
                requireActivity().toast(R.string.removed)
            }
            else -> {
                com.example.newsapp.util.startActivity(
                    requireActivity(), article!!, DetailsActivity::class.java
                )
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}