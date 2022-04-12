package com.example.autismpedia.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.autismpedia.R
import com.example.autismpedia.viewmodels.GameTypesViewModel

class GameTypesFragment : Fragment() {

    companion object {
        fun newInstance() = GameTypesFragment()
    }

    private lateinit var viewModel: GameTypesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.game_types_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(GameTypesViewModel::class.java)
        // TODO: Use the ViewModel
    }

}