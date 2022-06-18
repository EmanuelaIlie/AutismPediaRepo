package com.example.autismpedia.ui

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.autismpedia.R
import com.example.autismpedia.databinding.FragmentAddNewGameBinding
import com.example.autismpedia.databinding.GameIdeasFragmentBinding
import com.example.autismpedia.enums.GameType
import com.example.autismpedia.models.Game
import com.example.autismpedia.utils.Prefs
import com.example.autismpedia.utils.State
import com.example.autismpedia.viewmodelfactories.AddNewGameViewModelFactory
import com.example.autismpedia.viewmodelfactories.GameIdeasViewModelFactory
import com.example.autismpedia.viewmodels.AddNewGameViewModel
import com.example.autismpedia.viewmodels.GameIdeasViewModel
import kotlinx.coroutines.launch

class AddNewGameFragment : Fragment() {

    private lateinit var viewModel: AddNewGameViewModel
    private lateinit var binding: FragmentAddNewGameBinding
    private val args: AddNewGameFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val viewModelFactory = AddNewGameViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory)[AddNewGameViewModel::class.java]
        binding = FragmentAddNewGameBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.gameType = args.gameType
        setupObservers()

        return binding.root
    }

    private fun setupObservers() {
        viewModel.onAddGameImage.observe(viewLifecycleOwner, Observer {
            openGalleryForImage()
        })

        viewModel.onAddNewGame.observe(viewLifecycleOwner, Observer {
            when(args.gameType) {
                GameType.DIDACTIC -> {
                    if(!binding.etNewGameTitle.text.isNullOrEmpty() && !binding.etNewQuestionTitle.text.isNullOrEmpty()) {
                        lifecycleScope.launch {
                            val game = Game(title = binding.etNewGameTitle.text.toString(), question_title = binding.etNewQuestionTitle.text.toString())
                            addGameToFirebase(game)
                        }
                    } else {
                        Toast.makeText(requireContext(), "Please add game title", Toast.LENGTH_SHORT).show()
                    }
                }
                else -> {
                    if(!binding.etNewGameTitle.text.isNullOrEmpty()) {
                        lifecycleScope.launch {
                            val game = Game(title = binding.etNewGameTitle.text.toString())
                            addGameToFirebase(game)
                        }
                    } else {
                        Toast.makeText(requireContext(), "Please add game title", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
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

    private fun openGalleryForImage() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "image/*"
        resultLauncherImage.launch(intent)
    }

    private val resultLauncherImage = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if(result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            if (data?.data != null) {
                val fileUri: Uri = data.data!!
                Glide.with(requireView())
                    .load(fileUri)
                    .into(binding.ivAddNewGameListImage)
            }
        }
    }
}