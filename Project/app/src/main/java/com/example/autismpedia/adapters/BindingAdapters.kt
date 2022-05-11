package com.example.autismpedia.adapters

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
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

@BindingAdapter("itemImage")
fun ImageView.setItemImage(item: Game?) {
    item?.let {
        setImageResource(R.drawable.ic_google)
    }
}

