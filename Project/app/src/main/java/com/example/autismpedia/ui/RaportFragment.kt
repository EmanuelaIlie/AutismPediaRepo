package com.example.autismpedia.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.autismpedia.R
import com.example.autismpedia.viewmodels.RaportViewModel

class RaportFragment : Fragment() {

    companion object {
        fun newInstance() = RaportFragment()
    }

    private lateinit var viewModel: RaportViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.raport_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RaportViewModel::class.java)
        // TODO: Use the ViewModel
    }

}