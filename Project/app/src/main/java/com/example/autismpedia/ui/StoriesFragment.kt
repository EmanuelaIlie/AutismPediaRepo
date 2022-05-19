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
import com.example.autismpedia.databinding.FragmentStoriesBinding
import com.example.autismpedia.models.Game
import com.example.autismpedia.utils.State
import com.example.autismpedia.viewmodelfactories.StoriesViewModelFactory
import com.example.autismpedia.viewmodels.StoriesViewModel
import kotlinx.coroutines.launch
import java.util.*

class StoriesFragment : Fragment() {

    private lateinit var viewModel: StoriesViewModel
    private lateinit var binding: FragmentStoriesBinding
    private val args: StoriesFragmentArgs by navArgs()
    private lateinit var currentGame: Game
    private var currentImageNr = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val viewModelFactory = StoriesViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory)[StoriesViewModel::class.java]
        binding = FragmentStoriesBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.game = args.game

        setupObservers()

        return binding.root
    }

    private fun setupObservers() {
        viewModel.onAddImageToFirebase.observe(viewLifecycleOwner, Observer {
            currentGame = it.first
            currentImageNr = it.second
            openGalleryForImage()
        })
    }


    private fun openGalleryForImage() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "image/*"
        resultLauncher.launch(intent)
    }

    private val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
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
        val newList = currentGame.images.toMutableList()
        newList[currentImageNr] = fileName
        val newGame = currentGame.copy(images = newList)
        viewModel.onAddImageToFirebase(newGame, fileName, fileUri).collect() { state ->
            when(state) {
                is State.Loading -> {
                    Toast.makeText(requireContext(), "Loading", Toast.LENGTH_SHORT).show()
                }
                is State.Success -> {
                    Toast.makeText(requireContext(), "Added", Toast.LENGTH_SHORT).show()
                    when(currentImageNr) {
                        0 -> binding.ivImageZero.setImageURI(fileUri)
                        1 -> binding.ivImageOne.setImageURI(fileUri)
                        2 -> binding.ivImageTwo.setImageURI(fileUri)
                        3 -> binding.ivImageThree.setImageURI(fileUri)
                        4 -> binding.ivImageFour.setImageURI(fileUri)
                        5 -> binding.ivImageFive.setImageURI(fileUri)
                    }
                    binding.game = newGame
                    removeImage(currentGame.images[currentImageNr])
                }
                is State.Failed -> Toast.makeText(requireContext(), "Failed! ${state.message}", Toast.LENGTH_SHORT).show()

            }
        }
    }

    private fun removeImage(fileName: String) {
        lifecycleScope.launch {
            removeImageFromFirebaseStorage(fileName)
        }
    }

    private suspend fun removeImageFromFirebaseStorage(fileName: String) {
        viewModel.removeImageFromFirebaseStorage(currentGame, fileName).collect() { state ->
            when(state) {
                is State.Loading -> {
//                    Toast.makeText(requireContext(), "Attempting to remove image", Toast.LENGTH_LONG).show()
                }
                is State.Success -> {
//                    Toast.makeText(requireContext(), "Removed", Toast.LENGTH_SHORT).show()
                }
                is State.Failed -> Toast.makeText(requireContext(), "Failed! ${state.message}", Toast.LENGTH_SHORT).show()

            }
        }
    }
}