package com.example.autismpedia.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.autismpedia.R
import com.example.autismpedia.databinding.FragmentAddNewGameBinding
import com.example.autismpedia.databinding.GameIdeasFragmentBinding
import com.example.autismpedia.utils.Prefs
import com.example.autismpedia.viewmodelfactories.AddNewGameViewModelFactory
import com.example.autismpedia.viewmodelfactories.GameIdeasViewModelFactory
import com.example.autismpedia.viewmodels.AddNewGameViewModel
import com.example.autismpedia.viewmodels.GameIdeasViewModel

class AddNewGameFragment : Fragment() {

    private lateinit var viewModel: AddNewGameViewModel
    private lateinit var binding: FragmentAddNewGameBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val viewModelFactory = AddNewGameViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory)[AddNewGameViewModel::class.java]
        binding = FragmentAddNewGameBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        return binding.root
    }

}