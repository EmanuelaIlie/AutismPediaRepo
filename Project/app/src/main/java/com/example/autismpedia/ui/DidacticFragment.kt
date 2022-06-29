package com.example.autismpedia.ui

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.autismpedia.R
import com.example.autismpedia.databinding.FragmentDidacticBinding
import com.example.autismpedia.models.Game
import com.example.autismpedia.models.Minigame
import com.example.autismpedia.utils.Prefs
import com.example.autismpedia.utils.State
import com.example.autismpedia.viewmodelfactories.DidacticViewModelFactory
import com.example.autismpedia.viewmodels.DidacticViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*

class DidacticFragment : Fragment() {

    private lateinit var viewModel: DidacticViewModel
    private lateinit var binding: FragmentDidacticBinding
    private val args: DidacticFragmentArgs by navArgs()
    private var indexOfQuestion = 0
    private var answeredCorrectly = false
    private var correctAnswerIndex = 0
    private lateinit var prefs: Prefs
    private var currentImageNrToBeAdded = 0
    private var currentMinigame = Minigame()
    private lateinit var currentGame: Game

    @RequiresApi(Build.VERSION_CODES.M)
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
        prefs = Prefs(requireContext())
        binding.isAdminEnabled = prefs.adminEnabled
        if(prefs.adminEnabled) {
            binding.btnDidacticNext.isEnabled = true
        }

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setupObservers() {
        viewModel.onAddNewMinigameEvent.observe(viewLifecycleOwner, Observer {
            lifecycleScope.launch {
                addMinigame()
            }
        })

        viewModel.onAddNewImageEvent.observe(viewLifecycleOwner, Observer { pair ->
            currentGame = pair.first
            currentImageNrToBeAdded = pair.second
            openGalleryForImage()
        })

        viewModel.onNextMinigame.observe(viewLifecycleOwner, Observer {
            answeredCorrectly = false
            correctAnswerIndex = 0
            binding.btnDidacticNext.isEnabled = false
            binding.ivAnswerOne.strokeColor = resources.getColorStateList(R.color.color_full_transparent, null)
            binding.ivAnswerTwo.strokeColor = resources.getColorStateList(R.color.color_full_transparent, null)
            binding.ivAnswerThree.strokeColor = resources.getColorStateList(R.color.color_full_transparent, null)
            indexOfQuestion++
            if(prefs.adminEnabled) {
                binding.btnDidacticNext.isEnabled = true
            }
            setupFlows()
        })
        viewModel.onAnswerImageClicked.observe(viewLifecycleOwner, Observer { imageIndex ->
            if(imageIndex == correctAnswerIndex) {
                answeredCorrectly = true
                binding.btnDidacticNext.isEnabled = true
            }
            when(imageIndex) {
                1 -> {
                    binding.btnDidacticNext.isEnabled = correctAnswerIndex == 1
                    if(correctAnswerIndex == 1) {
                        binding.ivAnswerOne.strokeColor = resources.getColorStateList(R.color.color_green, null)
                    } else {
                        binding.ivAnswerOne.strokeColor = resources.getColorStateList(R.color.color_red, null)
                    }
                    binding.ivAnswerOne.strokeWidth = 17F
                    binding.ivAnswerTwo.strokeWidth = 0F
                    binding.ivAnswerThree.strokeWidth = 0F
                }
                2 -> {
                    if(correctAnswerIndex == 2) {
                        binding.ivAnswerTwo.strokeColor = resources.getColorStateList(R.color.color_green, null)
                    } else {
                        binding.ivAnswerTwo.strokeColor = resources.getColorStateList(R.color.color_red, null)
                    }
                    binding.btnDidacticNext.isEnabled = correctAnswerIndex == 2
                    binding.ivAnswerOne.strokeWidth = 0F
                    binding.ivAnswerTwo.strokeWidth = 17F
                    binding.ivAnswerThree.strokeWidth = 0F
                }
                3 -> {
                    if(correctAnswerIndex == 3) {
                        binding.ivAnswerThree.strokeColor = resources.getColorStateList(R.color.color_green, null)
                    } else {
                        binding.ivAnswerThree.strokeColor = resources.getColorStateList(R.color.color_red, null)
                    }
                    binding.btnDidacticNext.isEnabled = correctAnswerIndex == 3
                    binding.ivAnswerOne.strokeWidth = 0F
                    binding.ivAnswerTwo.strokeWidth = 0F
                    binding.ivAnswerThree.strokeWidth = 17F
                }
            }
        })
    }

    private fun setupFlows() {
        lifecycleScope.launch {
            loadMiniGames(args.game)
        }
    }

    private suspend fun addMinigame() {
        viewModel.onAddMinigameToFirebase(game = args.game).collect() { state ->
            when(state) {
                is State.Loading -> {
                    Toast.makeText(requireContext(), "Loading", Toast.LENGTH_SHORT).show()
                }
                is State.Success -> {
                    Toast.makeText(requireContext(), "Added minigame", Toast.LENGTH_SHORT).show()
                }
                is State.Failed -> {
                    Toast.makeText(requireContext(), "Failed! ${state.message}", Toast.LENGTH_SHORT).show()
                }
            }
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
                            currentMinigame = minigame
                        }
                    }
                    if(indexOfQuestion >= state.data.size - 1) {
                        binding.btnDidacticNext.visibility = View.INVISIBLE
                    }
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
                uploadImageToFirebase(fileUri)
            }
        }
    }

    private fun uploadImageToFirebase(fileUri: Uri) {
        lifecycleScope.launch {
            val fileName = UUID.randomUUID().toString()
            addImageIdToFirestore(fileName, fileUri)
        }
    }

    private suspend fun addImageIdToFirestore(fileName: String, fileUri: Uri) {
        val newMinigameList = currentMinigame.images.toMutableList()
        newMinigameList[currentImageNrToBeAdded] = fileName
        val newMinigame = currentMinigame.copy(images = newMinigameList)

        viewModel.onAddImageToFirebase(currentGame, fileName, fileUri, newMinigame).collect() { state ->
            when(state) {
                is State.Loading -> {
                    Toast.makeText(requireContext(), "Loading", Toast.LENGTH_SHORT).show()
                }
                is State.Success -> {
                    Toast.makeText(requireContext(), "Added", Toast.LENGTH_SHORT).show()
                    when(currentImageNrToBeAdded) {
                        0 -> binding.ivQuestion.setImageURI(fileUri)
                        1 -> binding.ivAnswerOne.setImageURI(fileUri)
                        2 -> binding.ivAnswerTwo.setImageURI(fileUri)
                        3 -> binding.ivAnswerThree.setImageURI(fileUri)
                    }
                    findNavController().popBackStack()
                }
                is State.Failed -> Toast.makeText(requireContext(), "Failed! ${state.message}", Toast.LENGTH_SHORT).show()

            }
        }
    }
}