package com.example.autismpedia.ui

import android.app.Activity
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.util.Log.e
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.startup.StartupLogger.e
import com.example.autismpedia.databinding.FragmentStoriesBinding
import com.example.autismpedia.models.Game
import com.example.autismpedia.utils.State
import com.example.autismpedia.viewmodelfactories.StoriesViewModelFactory
import com.example.autismpedia.viewmodels.StoriesViewModel
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*

class StoriesFragment : Fragment() {

    private lateinit var viewModel: StoriesViewModel
    private lateinit var binding: FragmentStoriesBinding
    private val args: StoriesFragmentArgs by navArgs()
    private lateinit var currentGame: Game
    private var currentImageNr = 0
    private var isImageEnlarged = false
    private lateinit var mediaPlayer: MediaPlayer

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
        prepareSound()
        binding.isSoundPlaying = false

        return binding.root
    }

    private fun prepareSound() {
        val storage = FirebaseStorage.getInstance()
        val gsReference = storage.getReferenceFromUrl("gs://autismpedia-e7d4a.appspot.com/SOUNDS/singing_birds.mp3")
        gsReference.downloadUrl.addOnSuccessListener { uri ->
            try {
                mediaPlayer = MediaPlayer().apply {
                    setAudioAttributes(
                        AudioAttributes.Builder()
                            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                            .setUsage(AudioAttributes.USAGE_MEDIA)
                            .build()
                    )
                    setDataSource(uri.toString())
                    prepare()
                }
            } catch (e: Exception) {
                Timber.e(e.message)
            }
        }
    }

    private fun setupOnClickSoundButton() {
        if(mediaPlayer.isPlaying) {
            binding.isSoundPlaying = false
            mediaPlayer.pause()
        } else {
            binding.isSoundPlaying = true
            mediaPlayer.start()
        }
    }

    private fun setupObservers() {
        viewModel.onAddImageToFirebase.observe(viewLifecycleOwner, Observer {
            currentGame = it.first
            currentImageNr = it.second
            openGalleryForImage()
        })
        viewModel.onPlaySoundClicked.observe(viewLifecycleOwner, Observer {
            setupOnClickSoundButton()
        })
        viewModel.onEnlargeImageEvent.observe(viewLifecycleOwner, Observer {
            currentImageNr = it
            when(currentImageNr) {
                0 ->  {
                    if (isImageEnlarged) {
                        binding.ivImageZero.layoutParams.height =
                            ViewGroup.LayoutParams.WRAP_CONTENT
                        binding.ivImageZero.layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT
                        isImageEnlarged = false
                    } else {
                        binding.ivImageZero.layoutParams.height =
                            ViewGroup.LayoutParams.MATCH_PARENT
                        binding.ivImageZero.layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
                        isImageEnlarged = true
                    }
                    binding.ivImageZero.requestLayout()
                }
                1 -> {
                    if (isImageEnlarged) {
                        binding.ivImageOne.layoutParams.height =
                            ViewGroup.LayoutParams.WRAP_CONTENT
                        binding.ivImageOne.layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT
                        isImageEnlarged = false
                    } else {
                        binding.ivImageOne.layoutParams.height =
                            ViewGroup.LayoutParams.MATCH_PARENT
                        binding.ivImageOne.layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
                        isImageEnlarged = true
                    }
                    binding.ivImageOne.requestLayout()
                }
                2 -> {
                    if (isImageEnlarged) {
                        binding.ivImageTwo.layoutParams.height =
                            ViewGroup.LayoutParams.WRAP_CONTENT
                        binding.ivImageTwo.layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT
                        isImageEnlarged = false
                    } else {
                        binding.ivImageTwo.layoutParams.height =
                            ViewGroup.LayoutParams.MATCH_PARENT
                        binding.ivImageTwo.layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
                        isImageEnlarged = true
                    }
                    binding.ivImageTwo.requestLayout()
                }
                3 -> {
                    if (isImageEnlarged) {
                        binding.ivImageThree.layoutParams.height =
                            ViewGroup.LayoutParams.WRAP_CONTENT
                        binding.ivImageThree.layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT
                        isImageEnlarged = false
                    } else {
                        binding.ivImageThree.layoutParams.height =
                            ViewGroup.LayoutParams.MATCH_PARENT
                        binding.ivImageThree.layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
                        isImageEnlarged = true
                    }
                    binding.ivImageThree.requestLayout()
                }
                4 -> {
                    if (isImageEnlarged) {
                        binding.ivImageFour.layoutParams.height =
                            ViewGroup.LayoutParams.WRAP_CONTENT
                        binding.ivImageFour.layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT
                        isImageEnlarged = false
                    } else {
                        binding.ivImageFour.layoutParams.height =
                            ViewGroup.LayoutParams.MATCH_PARENT
                        binding.ivImageFour.layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
                        isImageEnlarged = true
                    }
                    binding.ivImageFour.requestLayout()
                }
                5 -> {
                    if (isImageEnlarged) {
                        binding.ivImageFive.layoutParams.height =
                            ViewGroup.LayoutParams.WRAP_CONTENT
                        binding.ivImageFive.layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT
                        isImageEnlarged = false
                    } else {
                        binding.ivImageFive.layoutParams.height =
                            ViewGroup.LayoutParams.MATCH_PARENT
                        binding.ivImageFive.layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
                        isImageEnlarged = true
                    }
                    binding.ivImageFive.requestLayout()
                }
            }
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

    override fun onPause() {
        super.onPause()
        mediaPlayer.stop()
    }
}