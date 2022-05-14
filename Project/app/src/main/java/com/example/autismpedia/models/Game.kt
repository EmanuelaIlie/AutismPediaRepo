package com.example.autismpedia.models

import com.google.firebase.firestore.DocumentId

data class Game (
        @DocumentId
        val id: String? = "",
        val images: List<String> = listOf(""),
        val type: String? = "",
        val title: String? = "",
        val description: String? = "",
)