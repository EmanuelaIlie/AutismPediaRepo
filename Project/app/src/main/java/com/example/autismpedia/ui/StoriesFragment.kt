package com.example.autismpedia.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.autismpedia.R
import com.example.autismpedia.viewmodels.StoriesViewModel

class StoriesFragment : Fragment() {

    companion object {
        fun newInstance() = StoriesFragment()
    }

    private lateinit var viewModel: StoriesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_stories, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(StoriesViewModel::class.java)
        // TODO: Use the ViewModel
    }

}