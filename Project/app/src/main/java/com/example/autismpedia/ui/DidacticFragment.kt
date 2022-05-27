package com.example.autismpedia.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.autismpedia.R
import com.example.autismpedia.databinding.FragmentDidacticBinding
import com.example.autismpedia.databinding.GameIdeasFragmentBinding
import com.example.autismpedia.viewmodelfactories.DidacticViewModelFactory
import com.example.autismpedia.viewmodelfactories.GameIdeasViewModelFactory
import com.example.autismpedia.viewmodels.DidacticViewModel
import com.example.autismpedia.viewmodels.GameIdeasViewModel

class DidacticFragment : Fragment() {

    private lateinit var viewModel: DidacticViewModel
    private lateinit var binding: FragmentDidacticBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val viewModelFactory = DidacticViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory)[DidacticViewModel::class.java]
        binding = FragmentDidacticBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        return binding.root
    }
}