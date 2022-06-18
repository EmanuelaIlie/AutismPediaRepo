package com.example.autismpedia.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.autismpedia.adapters.GameAdapter
import com.example.autismpedia.adapters.GameListener
import com.example.autismpedia.databinding.GameIdeasFragmentBinding
import com.example.autismpedia.enums.GameType
import com.example.autismpedia.models.Game
import com.example.autismpedia.utils.Prefs
import com.example.autismpedia.utils.State
import com.example.autismpedia.viewmodelfactories.GameIdeasViewModelFactory
import com.example.autismpedia.viewmodels.GameIdeasViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*

class GameIdeasFragment : Fragment() {

    private lateinit var viewModel: GameIdeasViewModel
    private lateinit var binding: GameIdeasFragmentBinding
    private lateinit var adapter: GameAdapter
    private val args: GameIdeasFragmentArgs by navArgs()
    private lateinit var prefs: Prefs

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val viewModelFactory = GameIdeasViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory)[GameIdeasViewModel::class.java]
        binding = GameIdeasFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        prefs = Prefs(requireContext())
        setupOnClickItem()
        binding.gameList.adapter = adapter
        binding.isAdminEnabled = prefs.adminEnabled
        setupFlows()
        setupObservers()

        return binding.root
    }

    private fun setupObservers() {
        viewModel.onAddGameClicked.observe(viewLifecycleOwner, Observer {
            findNavController().navigate(GameIdeasFragmentDirections.actionGameIdeasFragmentToAddNewGameFragment())
//            lifecycleScope.launch {
//                val game = Game()
//                addGameToFirebase(game)
//            }
        })
    }

    private fun setupFlows() {
        lifecycleScope.launch {
            loadGames(args.gameType)
        }
    }

    private suspend fun addGameToFirebase(game: Game) {
        viewModel.onAddGameToFirebase(game, args.gameType).collect() { state ->
            when(state) {
                is State.Loading -> {
                    Toast.makeText(requireContext(), "Loading", Toast.LENGTH_SHORT).show()
                }
                is State.Success -> {
                    Toast.makeText(requireContext(), "Added", Toast.LENGTH_SHORT).show()
                }
                is State.Failed -> Toast.makeText(requireContext(), "Failed! ${state.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private suspend fun loadGames(gameType: GameType) {
        viewModel.getAllGames(gameType).collect() { state ->
            when(state) {
                is State.Loading -> {
                    Toast.makeText(requireContext(), "Loading", Toast.LENGTH_SHORT).show()
                }
                is State.Success -> {
                    adapter.submitList(state.data)
                }
                is State.Failed -> Toast.makeText(requireContext(), "Failed! ${state.message}", Toast.LENGTH_SHORT).show()

            }
        }
    }

    private fun setupOnClickItem() {
        adapter = GameAdapter(GameListener { game ->
            when(args.gameType) {
                GameType.STORY -> {
                    findNavController().navigate(GameIdeasFragmentDirections.actionGameIdeasFragmentToStoriesFragment(game))
                }
                GameType.DAILY_ACTIVITIES -> {
                    findNavController().navigate(GameIdeasFragmentDirections.actionGameIdeasFragmentToDailyActivitiesFragment(game))
                }
                GameType.DIDACTIC -> {
                    findNavController().navigate(GameIdeasFragmentDirections.actionGameIdeasFragmentToDidacticFragment(game))
                }
            }
        })
    }
}