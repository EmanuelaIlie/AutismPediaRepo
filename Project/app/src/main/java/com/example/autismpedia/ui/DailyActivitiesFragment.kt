package com.example.autismpedia.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.example.autismpedia.R
import com.example.autismpedia.databinding.FragmentDailyActivitiesBinding
import com.example.autismpedia.enums.DailyActivitiesType
import com.example.autismpedia.utils.Constants
import com.example.autismpedia.utils.Prefs
import com.example.autismpedia.utils.State
import com.example.autismpedia.viewmodelfactories.DailyActivitiesViewModelFactory
import com.example.autismpedia.viewmodels.DailyActivitiesViewModel
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*

class DailyActivitiesFragment : Fragment() {

    private lateinit var viewModel: DailyActivitiesViewModel
    private lateinit var binding: FragmentDailyActivitiesBinding
    private val args: DailyActivitiesFragmentArgs by navArgs()
    private lateinit var prefs: Prefs


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
        makeEditTextScrollable()
        prefs = Prefs(requireContext())
        binding.isAdminEnabled = prefs.adminEnabled
        setupVideo()
        setupObservers()

        return binding.root
    }

    private fun setupObservers() {
        viewModel.onAddNewVideoEvent.observe(viewLifecycleOwner, Observer {
            openGalleryForVideo()
        })
    }

    private fun setupVideo(videoName: String? = args.game.video) {
        val storage = FirebaseStorage.getInstance()
        val gsReference = storage.getReferenceFromUrl("${Constants.FIREBASE_STORAGE_REF}/${Constants.FIREBASE_STORAGE_VIDEO_FOLDER}/$videoName.mp4")
        gsReference.downloadUrl.addOnSuccessListener { uri ->
            try {
                val mediaControls = MediaController(requireContext())
                mediaControls.setAnchorView(binding.videoDaily)
                binding.videoDaily.setMediaController(mediaControls)
                binding.videoDaily.setVideoURI(uri)
                binding.videoDaily.requestFocus()
                binding.videoDaily.start()
            } catch (e: Exception) {
                Timber.e(e.message)
            }
        }
    }

    private fun setupFlows() {
        lifecycleScope.launch {
            loadDailyActivitiesText()
        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
               viewModel.onSaveTextClicked.collect { dailyActivitiesType ->
                   onAddDailyActivitiesTextToFirebase(dailyActivitiesType)
               }
            }
        }
    }

    private fun openGalleryForVideo() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Video.Media.EXTERNAL_CONTENT_URI)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        resultLauncherVideo.launch(intent)
    }

    private val resultLauncherVideo = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if(result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            if (data?.data != null) {
                val fileUri: Uri = data.data!!
                uploadVideoToFirebase(fileUri)
            }
        }
    }

    private fun uploadVideoToFirebase(fileUri: Uri) {
        lifecycleScope.launch {
            val fileName = UUID.randomUUID().toString()
            addVideoIdToFirestore(fileName, fileUri)
        }
    }

    private suspend fun addVideoIdToFirestore(fileName: String, fileUri: Uri) {
        viewModel.onAddVideoToFirebase(args.game, fileName, fileUri).collect() { state ->
            when(state) {
                is State.Loading -> {
                    Toast.makeText(requireContext(), "Loading", Toast.LENGTH_SHORT).show()
                }
                is State.Success -> {
                    Toast.makeText(requireContext(), "Added", Toast.LENGTH_SHORT).show()
                    setupVideo(fileName)
                }
                is State.Failed -> Toast.makeText(requireContext(), "Failed! ${state.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private suspend fun loadDailyActivitiesText() {
        viewModel.onGetDailyActivitiesTextFromFirebase(args.game).collect() { state ->
            when(state) {
                is State.Loading -> {
                    Toast.makeText(requireContext(), "Loading", Toast.LENGTH_SHORT).show()
                }
                is State.Success -> {
                    val stringNecessaryObjects = state.data?.necessary_objects?.replace("_b", "\n")
                    val stringSteps = state.data?.steps?.replace("_b", "\n")
                    binding.etAddObiecteNecesare.setText(stringNecessaryObjects)
                    binding.etAddSteps.setText(stringSteps)
                }
                is State.Failed -> Toast.makeText(requireContext(), "Failed! ${state.message}", Toast.LENGTH_SHORT).show()

            }
        }
    }

    private suspend fun onAddDailyActivitiesTextToFirebase(dailyActivitiesType: DailyActivitiesType) {
        val textNewline = when(dailyActivitiesType) {
            DailyActivitiesType.NECESSARY_OBJECTS -> {
                binding.etAddObiecteNecesare.text.toString().replace("\n", "_b")
            }
            DailyActivitiesType.STEPS -> {
                binding.etAddSteps.text.toString().replace("\n", "_b")
            }
        }

        val newGame = when(dailyActivitiesType) {
            DailyActivitiesType.NECESSARY_OBJECTS -> {
                args.game.copy(necessary_objects = textNewline)
            }
            DailyActivitiesType.STEPS -> {
                args.game.copy(steps = textNewline)
            }
        }

        viewModel.onAddDailyActivitiesTextToFirebase(newGame, dailyActivitiesType).collect() { state ->
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

    @SuppressLint("ClickableViewAccessibility")
    private fun makeEditTextScrollable() {
        binding.etAddObiecteNecesare.setOnTouchListener { view, event ->
            view.parent.requestDisallowInterceptTouchEvent(true)
            if ((event.action and MotionEvent.ACTION_MASK) == MotionEvent.ACTION_UP) {
                view.parent.requestDisallowInterceptTouchEvent(false)
            }
            return@setOnTouchListener false
        }
    }


}