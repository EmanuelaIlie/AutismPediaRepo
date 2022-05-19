package com.example.autismpedia.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.autismpedia.R
import com.example.autismpedia.databinding.FragmentDailyActivitiesBinding
import com.example.autismpedia.databinding.GameIdeasFragmentBinding
import com.example.autismpedia.viewmodelfactories.DailyActivitiesViewModelFactory
import com.example.autismpedia.viewmodelfactories.GameIdeasViewModelFactory
import com.example.autismpedia.viewmodels.DailyActivitiesViewModel
import com.example.autismpedia.viewmodels.GameIdeasViewModel

class DailyActivitiesFragment : Fragment() {

    private lateinit var viewModel: DailyActivitiesViewModel
    private lateinit var binding: FragmentDailyActivitiesBinding
    private val args: DailyActivitiesFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val viewModelFactory = DailyActivitiesViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory)[DailyActivitiesViewModel::class.java]
        binding = FragmentDailyActivitiesBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.game = args.game

        return binding.root
    }

}