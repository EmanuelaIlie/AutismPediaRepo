package com.example.autismpedia.ui

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.autismpedia.R

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class GoogleMapsFragment : Fragment() {

    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        val cluj1 = LatLng(46.791163, 23.643068)
        val cluj2 = LatLng(46.759358, 23.581747)
        val bucuresti1 = LatLng(44.421449, 26.094111)
        val bucuresti2 = LatLng(44.432378, 26.131837)
        val bucuresti3 = LatLng(44.420619, 26.141586)
        val bucuresti4 = LatLng(44.408438, 26.174960)
        val bucuresti5 = LatLng(44.399440, 26.104463)
        val timisoara1 = LatLng(45.7489, 21.2087)
        val timisoara2 = LatLng(45.753875, 21.246672)
        val timisoara3 = LatLng(45.748790, 21.220835)
        val timisoara4 = LatLng(45.746808, 21.196585)
        val timisoara5 = LatLng(45.726427, 21.222020)
        val timisoara6 = LatLng(45.725701, 21.188043)


        googleMap.addMarker(MarkerOptions().position(cluj1).title("Asociatia Autism Prietenie Terapie"))
        googleMap.addMarker(MarkerOptions().position(cluj2).title("Asociatia Autism Transilvania"))
        googleMap.addMarker(MarkerOptions().position(bucuresti1).title("Asociatia Romana de Terapii in Autism si ADHD"))
        googleMap.addMarker(MarkerOptions().position(bucuresti2).title("Asociatia Autism Voice"))
        googleMap.addMarker(MarkerOptions().position(bucuresti3).title("Asociația pentru Ajutorarea Copiilor cu Autism din România - Horia Moțoi"))
        googleMap.addMarker(MarkerOptions().position(bucuresti4).title("Autism Help Center Sun House"))
        googleMap.addMarker(MarkerOptions().position(bucuresti5).title("Centrul Autism Step by Step - Oltenitei"))
        googleMap.addMarker(MarkerOptions().position(timisoara1).title("Asociaţia Casa Faenza-Centrul Comunitar PT. Copii Autişti"))
        googleMap.addMarker(MarkerOptions().position(timisoara2).title("Day Center for Children with Disabilities Long Bridge"))
        googleMap.addMarker(MarkerOptions().position(timisoara3).title("Copii si Zane"))
        googleMap.addMarker(MarkerOptions().position(timisoara4).title("Society for Children and Parents"))
        googleMap.addMarker(MarkerOptions().position(timisoara5).title("Children and Fairies Center"))
        googleMap.addMarker(MarkerOptions().position(timisoara6).title("Foundation For You"))

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(timisoara1, 12.0f))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_google_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }
}