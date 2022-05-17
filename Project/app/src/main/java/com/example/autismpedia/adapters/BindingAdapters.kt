package com.example.autismpedia.adapters

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.autismpedia.R
import com.example.autismpedia.models.Game
import com.example.autismpedia.utils.Constants
import com.google.firebase.storage.FirebaseStorage

@BindingAdapter("itemTitle")
fun TextView.setTitle(item: Game?) {
    item?.let {
        text = item.title
    }
}

@BindingAdapter("itemDescription")
fun TextView.setDescription(item: Game?) {
    item?.let {
        text = item.description
    }
}

@BindingAdapter("loadImage")
fun loadImage(imageView: ImageView?, game: Game) {
    // Load image with your image loading library ,like with Glide or Picasso or any of your favourite
    val storage = FirebaseStorage.getInstance()
    val gsReference = storage.getReferenceFromUrl("gs://autismpedia-e7d4a.appspot.com/${game.type}/")

    gsReference.listAll()
        .addOnSuccessListener { listResult ->
            listResult.items.forEach { item ->
                if(item.toString().contains(game.id.toString())) {
                    val extension = item.toString().substringAfterLast(".")
                    val imageRef = storage.getReferenceFromUrl("gs://autismpedia-e7d4a.appspot.com/${game.type}/${game.id}.$extension")
                    imageView?.let { Glide.with(it.context).load(imageRef).into(imageView) }
                }
            }
        }
}

@BindingAdapter("loadImageParam", "imageNr")
fun loadImageParam(imageView: ImageView?, game: Game, imageNr: Int) {
    val storage = FirebaseStorage.getInstance()
    val gsReference = storage.getReferenceFromUrl("gs://autismpedia-e7d4a.appspot.com/${game.type}/${Constants.FIRESTORE_STORAGE_IMAGES_FOLDER}/")

    gsReference.listAll()
        .addOnSuccessListener { listResult ->
            listResult.items.forEach { storageItem ->
                if(storageItem.toString().contains(game.images[imageNr])) {
                    val extension = storageItem.toString().substringAfterLast(".")
                    val imageRef = storage.getReferenceFromUrl("gs://autismpedia-e7d4a.appspot.com/${game.type}/${Constants.FIRESTORE_STORAGE_IMAGES_FOLDER}/${game.images[imageNr]}.$extension")
                    imageView?.let { Glide.with(it.context).load(imageRef).into(imageView) }
                }
            }
        }

//        gsReference.listAll()
//        .addOnSuccessListener { listResult ->
//            listResult.items.forEach { storageItem ->
//                game.images.forEach { listItem ->
//                    if(storageItem.name == listItem) {
//                        val extension = storageItem.name.substringAfterLast(".")
//                        val imageRef = storage.getReferenceFromUrl("gs://autismpedia-e7d4a.appspot.com/${game.type}/${Constants.FIRESTORE_STORAGE_IMAGES_FOLDER}/${storageItem.name}.$extension")
//                        imageView?.let { Glide.with(it.context).load(imageRef).into(imageView) }
//                    }
//                }
//            }
//        }
}



