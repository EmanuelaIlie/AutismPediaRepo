package com.example.autismpedia.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.autismpedia.R
import com.example.autismpedia.databinding.FragmentStoriesBinding
import com.example.autismpedia.databinding.GameIdeasFragmentBinding
import com.example.autismpedia.utils.Constants
import com.example.autismpedia.viewmodels.GameIdeasViewModel
import com.example.autismpedia.viewmodels.StoriesViewModel
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class StoriesFragment : Fragment() {

    private lateinit var viewModel: StoriesViewModel
    private lateinit var binding: FragmentStoriesBinding
    private val args: StoriesFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[StoriesViewModel::class.java]
        binding = FragmentStoriesBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.game = args.game

        return binding.root
    }
}