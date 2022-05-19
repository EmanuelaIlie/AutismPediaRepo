package com.example.autismpedia.models

import com.google.firebase.firestore.DocumentId
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Game (
        @DocumentId
        @SerialName("id") val id: String? = "",
        @SerialName("images") val images: MutableList<String> = mutableListOf(""),
        @SerialName("type") val type: String? = "",
        @SerialName("title") val title: String? = "",
        @SerialName("description") val description: String? = "",
        @SerialName("necessary_objects") val necessary_objects: String? = "",
): java.io.Serializable