package com.example.autismpedia.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.autismpedia.adapters.GameAdapter
import com.example.autismpedia.databinding.GameIdeasFragmentBinding
import com.example.autismpedia.models.Game
import com.example.autismpedia.viewmodels.GameIdeasViewModel

class GameIdeasFragment : Fragment() {

    private lateinit var viewModel: GameIdeasViewModel
    private lateinit var binding: GameIdeasFragmentBinding
    private lateinit var adapter: GameAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[GameIdeasViewModel::class.java]
        binding = GameIdeasFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        adapter = GameAdapter()
        binding.gameList.adapter = adapter

        val gameList = listOf<Game>(
            Game("abc", "title", "desc"),
            Game("abc1", "title1", "desc1"),
            Game("abc2", "title2", "desc2"),
            Game("abc3", "title3", "desc3"),Game("abc", "title", "desc"),
            Game("abc1", "title1", "desc1"),
            Game("abc2", "title2", "desc2"),
            Game("abc3", "title3", "desc3"),Game("abc", "title", "desc"),
            Game("abc1", "title1", "desc1"),
            Game("abc2", "title2", "desc2"),
            Game("abc3", "title3", "desc3"),Game("abc", "title", "desc"),
            Game("abc1", "title1", "desc1"),
            Game("abc2", "title2", "desc2"),
            Game("abc3", "title3", "desc3"),Game("abc", "title", "desc"),
            Game("abc1", "title1", "desc1"),
            Game("abc2", "title2", "desc2"),
            Game("abc3", "title3", "desc3"),
        )

        adapter.submitList(gameList)

        return binding.root
    }
}