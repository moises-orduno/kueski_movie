package com.example.mazeapi.network.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.annotations.SerializedName
@Entity
data class MovieResult(
    @SerializedName("adult") var adult: Boolean? = null,
    @SerializedName("backdrop_path") var backdropPath: String? = null,
    @SerializedName("genre_ids") var genreIds: ArrayList<Int> = arrayListOf(),
    @PrimaryKey
    @SerializedName("id") var id: Int,
    @SerializedName("original_language") var originalLanguage: String? = null,
    @SerializedName("original_title") var originalTitle: String? = null,
    @SerializedName("overview") var overview: String? = null,
    @SerializedName("popularity") var popularity: Double? = null,
    @SerializedName("poster_path") var posterPath: String? = null,
    @SerializedName("release_date") var releaseDate: String? = null,
    @SerializedName("title") var title: String? = null,
    @SerializedName("video") var video: Boolean? = null,
    @SerializedName("vote_average") var voteAverage: Double? = null,
    @SerializedName("vote_count") var voteCount: Int? = null
)

class Converters {

    @TypeConverter
    fun fromGenreIds(value: ArrayList<Int>?): String? {
        return value?.joinToString(",")
    }

    @TypeConverter
    fun toGenreIds(value: String?): ArrayList<Int>? {
        return value?.split(",")?.map { it.toInt() }?.let { ArrayList(it) }
    }
}
