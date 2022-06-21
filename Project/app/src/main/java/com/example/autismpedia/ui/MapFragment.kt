package com.example.autismpedia.ui

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.Point
import android.graphics.Rect
import android.graphics.RectF
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.autismpedia.R
import com.example.autismpedia.databinding.GameIdeasFragmentBinding
import com.example.autismpedia.databinding.MapFragmentBinding
import com.example.autismpedia.viewmodelfactories.MapViewModelFactory
import com.example.autismpedia.viewmodels.GameIdeasViewModel
import com.example.autismpedia.viewmodels.MapViewModel

class MapFragment : Fragment() {

    private lateinit var viewModel: MapViewModel
    private lateinit var binding: MapFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val viewModelFactory = MapViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory)[MapViewModel::class.java]
        binding = MapFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        return binding.root
    }
}