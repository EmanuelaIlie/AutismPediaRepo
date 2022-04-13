package com.example.autismpedia.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.autismpedia.databinding.GameTypesFragmentBinding
import com.example.autismpedia.viewmodels.GameTypesViewModel

class GameTypesFragment : Fragment() {


    private lateinit var binding: GameTypesFragmentBinding
    private lateinit var viewModel: GameTypesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[GameTypesViewModel::class.java]
        binding = GameTypesFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        setupObserves()

        return binding.root
    }

    private fun setupObserves() {
        viewModel.onNavigateToGameIdeas.observe(viewLifecycleOwner, Observer {
            findNavController().navigate(GameTypesFragmentDirections.actionGameTypesFragmentToGameIdeasFragment())
        })
    }

}