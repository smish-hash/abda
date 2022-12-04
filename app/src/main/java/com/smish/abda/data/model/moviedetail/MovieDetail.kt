package com.smish.abda.data.model.moviedetail


import com.google.gson.annotations.SerializedName

data class MovieDetail(
    @SerializedName("Actors")
    val actors: String? = null,
    @SerializedName("Awards")
    val awards: String? = null,
    @SerializedName("BoxOffice")
    val boxOffice: String? = null,
    @SerializedName("Country")
    val country: String? = null,
    @SerializedName("DVD")
    val dVD: String? = null,
    @SerializedName("Director")
    val director: String? = null,
    @SerializedName("Genre")
    val genre: String? = null,
    @SerializedName("imdbID")
    val imdbID: String? = null,
    @SerializedName("imdbRating")
    val imdbRating: String? = null,
    @SerializedName("imdbVotes")
    val imdbVotes: String? = null,
    @SerializedName("Language")
    val language: String? = null,
    @SerializedName("Metascore")
    val metascore: String? = null,
    @SerializedName("Plot")
    val plot: String? = null,
    @SerializedName("Poster")
    val poster: String? = null,
    @SerializedName("Production")
    val production: String? = null,
    @SerializedName("Rated")
    val rated: String? = null,
    @SerializedName("Ratings")
    val ratings: List<Rating?>? = null,
    @SerializedName("Released")
    val released: String? = null,
    @SerializedName("Response")
    val response: String? = null,
    @SerializedName("Error")
    val error: String? = null,
    @SerializedName("Runtime")
    val runtime: String? = null,
    @SerializedName("Title")
    val title: String? = null,
    @SerializedName("Type")
    val type: String? = null,
    @SerializedName("Website")
    val website: String? = null,
    @SerializedName("Writer")
    val writer: String? = null,
    @SerializedName("Year")
    val year: String? = null
)

fun MovieDetail.toArray(str: String): List<String> {
    val list = str.split(", ").toList()
    return list
}