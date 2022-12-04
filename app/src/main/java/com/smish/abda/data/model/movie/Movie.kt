package com.smish.abda.data.model.movie


import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("Response")
    val response: String? = null,
    @SerializedName("Search")
    val movies: List<Search?>? = null,
    @SerializedName("totalResults")
    val totalResults: String? = null,
    @SerializedName("Error")
    val error: String? = null
)
