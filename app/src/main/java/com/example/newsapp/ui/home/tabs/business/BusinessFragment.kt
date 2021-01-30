package com.example.newsapp.ui.home.tabs.business

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
import com.example.newsapp.databinding.FragmentBusinessBinding
import com.example.newsapp.ui.details.DetailsActivity
import com.example.newsapp.util.Constants
import com.example.newsapp.util.OnItemClickListener
import com.example.newsapp.util.hide


/**
 * A simple [Fragment] subclass.
 */
class BusinessFragment : Fragment(), OnItemClickListener {

    private var _binding: FragmentBusinessBinding? = null
    private val binding get() = _binding!!
    private val homeAdapter = HomeAdapter(this)
    private lateinit var viewModel: BusinessViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentBusinessBinding.inflate(inflater, container, false)

        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        viewModel = ViewModelProvider(this).get(BusinessViewModel::class.java)

        viewModel.newsPagedList.observe(viewLifecycleOwner, {
            homeAdapter.submitList(it)
            binding.recyclerView.adapter = homeAdapter
        })
        binding.loadingIndicator.hide()

        return binding.root
    }


    override fun onItemClick(position: Article?) {
        val intent = Intent(activity, DetailsActivity::class.java)
        intent.putExtra(Constants.MODEL,position)
        intent.putExtra("source","Business")
        context!!.startActivity(intent)
    }
}