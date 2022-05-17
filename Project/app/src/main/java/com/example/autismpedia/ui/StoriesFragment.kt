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
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.autismpedia.R
import com.example.autismpedia.databinding.FragmentStoriesBinding
import com.example.autismpedia.databinding.GameIdeasFragmentBinding
import com.example.autismpedia.utils.Constants
import com.example.autismpedia.utils.Constants.REQUEST_CODE
import com.example.autismpedia.viewmodels.GameIdeasViewModel
import com.example.autismpedia.viewmodels.StoriesViewModel
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class StoriesFragment : Fragment() {

    private lateinit var viewModel: StoriesViewModel
    private lateinit var binding: FragmentStoriesBinding
    private val args: StoriesFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[StoriesViewModel::class.java]
        binding = FragmentStoriesBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.game = args.game

        openGalleryForImage()

        return binding.root
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
                binding.ivImageOne.setImageURI(imageUri)
            }
        }
    }

}