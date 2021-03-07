package com.example.newsapp.ui.wishlist

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.R
import com.example.newsapp.adapter.WishlistAdapter
import com.example.newsapp.data.model.Article
import com.example.newsapp.databinding.FragmentWishlistBinding
import com.example.newsapp.ui.details.DetailsActivity
import com.example.newsapp.util.*
import dagger.hilt.android.AndroidEntryPoint


/**
 * A simple [Fragment] subclass.
 */
@AndroidEntryPoint
class WishlistFragment : Fragment(), OnItemClickListener, View.OnClickListener {

    // Initialization
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

        // Assignment
        wishlistAdapter = WishlistAdapter(this)
        viewModel = ViewModelProvider(this).get(WishlistViewModel::class.java)
        // Observe the changes from live data
        viewModel.sendRequest().observe(viewLifecycleOwner, {
            updateUi(it)
        })

        binding.floatingButton.setOnClickListener(this)
        return binding.root
    }


    override fun onClick(v: View) {
        if (v.id == R.id.floating_button)
            openDialog()
    }


    override fun onItemClick(article: Article?, operation: String) {
        when (operation) {
            "delete" -> {
                viewModel.sendDeleteRequest(article).observe(viewLifecycleOwner, {
                    updateUi(it)
                })
            }
            "share" -> {
                Intent().apply {
                    if (article != null) {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, article.url)
                        type = "text/plain"
                        startActivity(this)
                    } else
                        requireActivity().toast(R.string.missingUrl)
                }
            }
            else ->
                startActivity(
                    requireActivity(), article!!, DetailsActivity::class.java
                )
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    // Update the list of articles
    private fun updateUi(list: List<Article>) {
        binding.apply {
            // Check if the list is empty
            if (list.isEmpty()) {
                hideVisibility(recyclerView, tvNoContent, tvDescription, ivNoArticles)
            } else {
                showVisibility(recyclerView, tvNoContent, tvDescription, ivNoArticles)
                recyclerView.layoutManager = LinearLayoutManager(activity)
                recyclerView.adapter = wishlistAdapter
                wishlistAdapter.submitList(list)
            }
        }
    }


    // Open alert dialog
    private fun openDialog() {
        val builder = AlertDialog.Builder(requireActivity())
        builder.setTitle(R.string.deleteAll)
            .setMessage(R.string.deleteMess)
            .setPositiveButton((R.string.delete)) { _, _ ->
                viewModel.deleteAll().observe(viewLifecycleOwner) {
                    updateUi(it)
                }
            }
        builder.create().show()
    }
}