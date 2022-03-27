package com.example.autismpedia.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.autismpedia.MainActivity
import com.example.autismpedia.databinding.SplashScreenFragmentBinding
import com.example.autismpedia.viewmodels.SplashScreenViewModel
import com.google.firebase.auth.FirebaseAuth

class SplashScreenFragment : Fragment() {


    private lateinit var viewModel: SplashScreenViewModel
    private lateinit var binding: SplashScreenFragmentBinding
    private lateinit var mAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[SplashScreenViewModel::class.java]
        binding = SplashScreenFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        mAuth = FirebaseAuth.getInstance()
        val user = mAuth.currentUser

        if (user == null) {
            findNavController().navigate(SplashScreenFragmentDirections.actionSplashScreenFragmentToLoginFragment())
        } else {
            val intent = Intent(requireActivity(), MainActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }

        return binding.root
    }



}