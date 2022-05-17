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
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.example.autismpedia.databinding.FragmentStoriesBinding
import com.example.autismpedia.enums.GameType
import com.example.autismpedia.models.Game
import com.example.autismpedia.utils.Constants
import com.example.autismpedia.viewmodels.StoriesViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
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
        viewModel = ViewModelProvider(this)[StoriesViewModel::class.java]
        binding = FragmentStoriesBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.game = args.game

        setupObservers()

        return binding.root
    }

    private fun setupObservers() {
        viewModel.onAddImage.observe(viewLifecycleOwner, Observer {
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
                val imageUri: Uri = data.data!!
                uploadImageToFirebase(imageUri)
            }
        }
    }

    private fun uploadImageToFirebase(fileUri: Uri) {
        if (fileUri != null) {
            val fileName = UUID.randomUUID().toString()
            val extension = ".jpg"
            val refStorage = FirebaseStorage.getInstance().reference.child("STORY/images/$fileName$extension")

            refStorage.putFile(fileUri)
                .addOnSuccessListener { taskSnapshot ->
                    taskSnapshot.storage.downloadUrl.addOnSuccessListener {
                        val imageUrl = it.toString()
                        addNewImageIdToFirestore(fileName)
                    }
                }

                .addOnFailureListener { e ->
                    print(e.message)
                }
        }
    }

    private fun addNewImageIdToFirestore(fileName: String) {
        val mGameCollection = FirebaseFirestore.getInstance()
        currentGame.images[currentImageNr] = fileName

        val gameRef = when(GameType.from(currentGame.type.toString())) {
            GameType.STORY -> {
                mGameCollection.collection(Constants.FIRESTORE_STORIES_COLLECTION).document(currentGame.id.toString()).update("images", currentGame.images)
            }
            GameType.DIDACTIC -> {
                mGameCollection.collection(Constants.FIRESTORE_DIDACTIC_COLLECTION).document(currentGame.id.toString()).update("images", currentGame.images)
            }
            GameType.DAILY_ACTIVITIES -> {
                mGameCollection.collection(Constants.FIRESTORE_DAILY_ACTIVITIES_COLLECTION).document(currentGame.id.toString()).update("images", currentGame.images)
            }
        }

    }

}