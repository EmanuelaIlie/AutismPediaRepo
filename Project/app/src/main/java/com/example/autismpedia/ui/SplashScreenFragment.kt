package com.example.autismpedia.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.autismpedia.databinding.SplashScreenFragmentBinding
import com.example.autismpedia.viewmodels.SplashScreenViewModel

class SplashScreenFragment : Fragment() {


    private lateinit var viewModel: SplashScreenViewModel
    private lateinit var binding: SplashScreenFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[SplashScreenViewModel::class.java]
        binding = SplashScreenFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel



        return binding.root
    }



}