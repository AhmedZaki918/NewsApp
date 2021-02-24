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
import com.example.newsapp.databinding.FragmentWishlistBinding
import com.example.newsapp.ui.details.DetailsActivity
import com.example.newsapp.util.Constants
import dagger.hilt.android.AndroidEntryPoint


/**
 * A simple [Fragment] subclass.
 */
@AndroidEntryPoint
class WishlistFragment : Fragment(), WishlistListener {


    private var _binding: FragmentWishlistBinding? = null
    private val binding get() = _binding!!
    private lateinit var wishlistAdapter: WishlistAdapter
    private lateinit var viewModel: WishlistViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as AppCompatActivity).supportActionBar?.title = "Saved"
        // Inflate the layout for this fragment
        _binding = FragmentWishlistBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this).get(WishlistViewModel::class.java)
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
        requireContext().startActivity(intent)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    // Update the list of articles
    private fun updateUi(list: List<Article>) {
        wishlistAdapter = WishlistAdapter(this)
        wishlistAdapter.submitList(list)
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
            adapter = wishlistAdapter
        }
    }
}