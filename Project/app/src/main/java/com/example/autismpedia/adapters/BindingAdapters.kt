package com.example.autismpedia.adapters

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.autismpedia.R
import com.example.autismpedia.models.Game

@BindingAdapter("itemTitle")
fun TextView.setDescription(item: Game?) {
    item?.let {
        text = item.description
    }
}

@BindingAdapter("description")
fun TextView.setUsage(item: Game?) {
    item?.let {
        text = item.description.toString()
    }
}

//@BindingAdapter("loadImage")
//fun loadImage(imageView: ImageView?, game: Game) {
//    // Load image with your image loading library ,like with Glide or Picasso or any of your favourite
//    val storage = FirebaseStorage.getInstance()
//    val gsReference = storage.getReferenceFromUrl("gs://skirentallicenta-ef1a0.appspot.com/${game.type}/")
//
//    gsReference.listAll()
//        .addOnSuccessListener { listResult ->
//            listResult.items.forEach { item ->
//                if(item.toString().contains(game.id)) {
//                    val extension = item.toString().substringAfterLast(".")
//                    val imageRef = storage.getReferenceFromUrl("gs://skirentallicenta-ef1a0.appspot.com/${game.type}/${game.id}.$extension")
//                    imageView?.let { Glide.with(it.context).load(imageRef).into(imageView) }
//                }
//            }
//        }
//
//}

