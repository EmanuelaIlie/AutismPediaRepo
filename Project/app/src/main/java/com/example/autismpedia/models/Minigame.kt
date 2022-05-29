package com.example.autismpedia.models

import com.google.firebase.firestore.DocumentId
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Minigame (
    @DocumentId
    @SerialName("id") val id: String? = "",
    @SerialName("images") val images: MutableList<String> = mutableListOf(""),
    @SerialName("right_answer") val right_answer: Int? = 1,
): java.io.Serializable