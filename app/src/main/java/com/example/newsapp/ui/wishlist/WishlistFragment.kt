package com.example.newsapp.ui.wishlist

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.adapter.WishlistAdapter
import com.example.newsapp.data.model.Article
import com.example.newsapp.data.repository.WishlistRepo
import com.example.newsapp.databinding.FragmentWishlistBinding
import com.example.newsapp.ui.details.DetailsActivity
import com.example.newsapp.util.Constants


/**
 * A simple [Fragment] subclass.
 */
class WishlistFragment : Fragment(), WishlistListener {


    private var _binding: FragmentWishlistBinding? = null
    private val binding get() = _binding!!
    private lateinit var wishlistAdapter: WishlistAdapter
    private lateinit var viewModel: WishlistViewModel
    private lateinit var factory: WishlistViewModelFactory
    private lateinit var repo: WishlistRepo


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as AppCompatActivity).supportActionBar?.title = "Saved"
        // Inflate the layout for this fragment
        _binding = FragmentWishlistBinding.inflate(inflater, container, false)

        initViews()
        // Observe the changes from live data
        viewModel.sendRequest().observe(viewLifecycleOwner, {
            updateUi(it)
        })
        return binding.root
    }


    override fun onDelete(article: Article?) {
        viewModel.sendDeleteRequest(article).observe(viewLifecycleOwner, {
            updateUi(it)
        })
    }


    override fun onShare(article: Article?) {
        TODO("Not yet implemented")
    }


    override fun onItemClick(article: Article?) {
        val intent = Intent(activity, DetailsActivity::class.java)
        intent.putExtra(Constants.MODEL, article)
        context!!.startActivity(intent)
    }


    // Initialize views
    private fun initViews() {
        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        wishlistAdapter = WishlistAdapter(this)
        repo = WishlistRepo(activity!!.application)
        factory = WishlistViewModelFactory(repo)
        viewModel = ViewModelProvider(this, factory).get(WishlistViewModel::class.java)
    }


    // Update the list of articles
    private fun updateUi(list: List<Article>) {
        wishlistAdapter.submitList(list)
        binding.recyclerView.apply {
            setHasFixedSize(true)
            adapter = wishlistAdapter
        }
    }
}