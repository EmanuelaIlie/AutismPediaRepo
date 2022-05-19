package com.example.autismpedia.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.example.autismpedia.R
import com.example.autismpedia.databinding.FragmentDailyActivitiesBinding
import com.example.autismpedia.databinding.GameIdeasFragmentBinding
import com.example.autismpedia.models.Game
import com.example.autismpedia.utils.State
import com.example.autismpedia.viewmodelfactories.DailyActivitiesViewModelFactory
import com.example.autismpedia.viewmodelfactories.GameIdeasViewModelFactory
import com.example.autismpedia.viewmodels.DailyActivitiesViewModel
import com.example.autismpedia.viewmodels.GameIdeasViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

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
        setupFlows()

        return binding.root
    }

    private fun setupFlows() {
        lifecycleScope.launch {
            loadNecesarryItems()
        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
               viewModel.onSaveNecessaryObjectsClicked.collect {
                   onAddNecessaryObjectsToFirebase()
               }
            }
        }
    }

    private suspend fun loadNecesarryItems() {
        viewModel.onGetNecessaryObjectsToFirebase(args.game).collect() { state ->
            when(state) {
                is State.Loading -> {
                    Toast.makeText(requireContext(), "Loading", Toast.LENGTH_SHORT).show()
                }
                is State.Success -> {
                    val string = state.data?.necessary_objects?.replace("_b", "\n")
                    binding.etAddObiecteNecesare.setText(string)
                }
                is State.Failed -> Toast.makeText(requireContext(), "Failed! ${state.message}", Toast.LENGTH_SHORT).show()

            }
        }
    }

    private suspend fun onAddNecessaryObjectsToFirebase() {
        val textNewline = binding.etAddObiecteNecesare.text.toString().replace("\n", "_b")
        val newGame = args.game.copy(necessary_objects = textNewline)
        viewModel.onAddNecessaryObjectsToFirebase(newGame).collect() { state ->
            when(state) {
                is State.Loading -> {
                    Toast.makeText(requireContext(), "Saving...", Toast.LENGTH_SHORT).show()
                }
                is State.Success -> {
                    Toast.makeText(requireContext(), "Saved", Toast.LENGTH_SHORT).show()
                    binding.game = newGame
                }
                is State.Failed -> Toast.makeText(requireContext(), "Failed! ${state.message}", Toast.LENGTH_SHORT).show()

            }
        }
    }

}