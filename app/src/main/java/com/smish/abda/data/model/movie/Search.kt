package com.smish.abda.data.model.movie


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "movies")
data class Search(
    @PrimaryKey
    @ColumnInfo(name = "imdbID")
    @SerializedName("imdbID")
    val imdbID: String,

    @SerializedName("Poster")
    val poster: String,

    @SerializedName("Title")
    val title: String,

    @SerializedName("Type")
    val type: String,

    @SerializedName("Year")
    val year: String
)

// since all are type string here, we do not create any type converter for room db else we would have needed one.
/*
class Converters {
    @TypeConverter
    fun fromsource(source: CustomType): String{
        return source. name

    @TypeConverter
    fun toSource(name:CustomType):Source{
        return CustomType(name.[object])
*/

// after this we would have needed to add this in the database class as well
/*
@TypeConverters(Converters::class)
abstract class MovieDatabase: RoomDatabase(){...}
*/