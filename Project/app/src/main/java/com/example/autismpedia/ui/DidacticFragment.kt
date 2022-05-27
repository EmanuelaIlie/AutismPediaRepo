package com.example.autismpedia.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.autismpedia.databinding.FragmentDidacticBinding
import com.example.autismpedia.enums.GameType
import com.example.autismpedia.models.Game
import com.example.autismpedia.utils.State
import com.example.autismpedia.viewmodelfactories.DidacticViewModelFactory
import com.example.autismpedia.viewmodels.DidacticViewModel
import kotlinx.coroutines.launch
import kotlin.math.min

class DidacticFragment : Fragment() {

    private lateinit var viewModel: DidacticViewModel
    private lateinit var binding: FragmentDidacticBinding
    private val args: DidacticFragmentArgs by navArgs()
    private var indexOfQuestion = 0
    private var answeredCorrectly = false
    private var correctAnswerIndex = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val viewModelFactory = DidacticViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory)[DidacticViewModel::class.java]
        binding = FragmentDidacticBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.game = args.game
        setupFlows()
        setupObservers()

        return binding.root
    }

    private fun setupObservers() {
        viewModel.onNextMinigame.observe(viewLifecycleOwner, Observer {
            answeredCorrectly = false
            correctAnswerIndex = 0
            indexOfQuestion = 0
            binding.btnDidacticNext.isEnabled = false
            indexOfQuestion++
            setupFlows()
        })
        viewModel.onAnswerImageClicked.observe(viewLifecycleOwner, Observer { imageIndex ->
            if(imageIndex == correctAnswerIndex) {
                answeredCorrectly = true
                binding.btnDidacticNext.isEnabled = true
            }
        })
    }

    private fun setupFlows() {
        lifecycleScope.launch {
            loadMiniGames(args.game)
        }
    }

    private suspend fun loadMiniGames(game: Game) {
        viewModel.getAllMinigames(game).collect() { state ->
            when(state) {
                is State.Loading -> {
                    Toast.makeText(requireContext(), "Loading", Toast.LENGTH_SHORT).show()
                }
                is State.Success -> {
                    if(indexOfQuestion < state.data.size) {
                        val minigame = state.data[indexOfQuestion]
                        if(minigame?.right_answer != null) {
                            correctAnswerIndex = minigame.right_answer
                            binding.miniGame = minigame
                        }
                    }
                }
                is State.Failed -> Toast.makeText(requireContext(), "Failed! ${state.message}", Toast.LENGTH_SHORT).show()

            }
        }
    }
}